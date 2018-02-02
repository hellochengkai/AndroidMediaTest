package com.example.chengkai.mediamuxer;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String testVideo = "/sdcard/video/8000160_1.ts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testMediaExtractor();
    }
    public void testMediaExtractor()
    {
        MediaExtractorTest mediaExtractorTest = new MediaExtractorTest(testVideo);
        mediaExtractorTest.getMediaStreamInfo();
        mediaExtractorTest.extractorByMime("video");
        mediaExtractorTest.extractorByMime("audio");
    }
}
