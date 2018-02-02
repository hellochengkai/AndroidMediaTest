package com.example.chengkai.mediamuxer;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by chengkai on 18-1-30.
 */

public class MediaMuxerTest {
    private static final String TAG = "MediaMuxerTest";
    private MediaMuxer mediaMuxer = null;

    public MediaMuxerTest(String url)
    {
    }
}
