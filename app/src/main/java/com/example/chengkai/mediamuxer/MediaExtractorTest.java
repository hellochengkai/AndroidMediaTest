package com.example.chengkai.mediamuxer;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by chengkai on 18-1-31.
 */

public class MediaExtractorTest {
    private static final String TAG = "MediaExtractorTest";
    private String url;

    public MediaExtractorTest(String url) {
        this.url = url;
    }

    public void getMediaStreamInfo(){
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int tracks = mediaExtractor.getTrackCount();
        if(tracks > 0) {
            for (int i = 0;i < tracks;i ++){
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                if(mediaExtractor != null){
                    Log.d(TAG, "getMediaStreamInfo: " + String.format("[%d]:%s",i,mediaFormat));
                }
            }
        }
    }

    // 最大缓冲区(1024 x 1024 = 1048576、1920 x 1080 = 2073600)
    // 由于没有录制1080P的视频，因此用1024的Buffer来缓存
    private static final int MAX_BUFF_SIZE = 1048576;
    public void extractorByMime(String mime){
        byte[] bytes = new byte[MAX_BUFF_SIZE];
        ByteBuffer mReadBuf = ByteBuffer.allocate(MAX_BUFF_SIZE);
        mReadBuf.clear();
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaFormat mediaFormatMime = null;
        int mimeIndex = -1;
        int tracks = mediaExtractor.getTrackCount();
        if(tracks > 0) {
            for (int i = 0;i < tracks;i ++){
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                if(mediaExtractor != null){
                    if(mediaFormat.getString("mime").startsWith(mime)){
                        mediaFormatMime = mediaFormat;
                        mimeIndex = i;
                    }
                    Log.d(TAG, "getMediaStreamInfo: " + String.format("[%d]:%s",i,mediaFormat));
                }
            }
        }
        if(mediaFormatMime == null || mimeIndex == -1){
            Log.d(TAG, "ExtractorVideo: " + "not find " + mime +" stream ");
            return;
        }
        mediaExtractor.selectTrack(mimeIndex);
        File file = new File(url + "." + mime);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(true){
            mReadBuf.clear();
            int sampleSize = mediaExtractor.readSampleData(mReadBuf,0);
            if(sampleSize < 0)
                break;
            mReadBuf.get(bytes,0,sampleSize);
            try {
                outputStream.write(bytes,0,sampleSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaExtractor.advance();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaExtractor.release();
    }
}
