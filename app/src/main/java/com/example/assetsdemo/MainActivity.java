package com.example.assetsdemo;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mAssetsTextBtn;
    private Button mAssetsImgBtn;
    private Button mAssetsVideoBtn;
    private TextView mTextShowTv;
    private ImageView mImgShowIv;
    private VideoView mVideoShowVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAssetsTextBtn = findViewById(R.id.assets_text_btn);
        mAssetsImgBtn = findViewById(R.id.assets_img_btn);
        mAssetsVideoBtn = findViewById(R.id.assets_video_btn);
        mTextShowTv = findViewById(R.id.text_show_tv);
        mImgShowIv = findViewById(R.id.img_show_iv);
        mVideoShowVv = findViewById(R.id.video_show_vv);
        initListener();
    }

    private void initListener() {
        mAssetsTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAssetsTextFile();
                showText();
            }
        });

        mAssetsImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAssetsImgFile();
            }
        });

        mAssetsVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAssetsVideoFile();
                playAssetsVideo();
            }
        });
    }

    private void playAssetsVideo() {
        File file = new File(getCacheDir() + File.separator + "videos", " anim.mp4");
        if (file.exists()) {
            MediaController mediaController = new MediaController(this);
            Log.i(TAG, "video exists");
            mVideoShowVv.setVisibility(View.VISIBLE);
            mVideoShowVv.setVideoPath(file.getAbsolutePath());
            mVideoShowVv.setMediaController(mediaController);
            mediaController.setMediaPlayer(mVideoShowVv);
            mVideoShowVv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                                mVideoShowVv.setBackgroundColor(Color.TRANSPARENT);
                            }
                            return true;
                        }
                    });
                }
            });
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(0);
            mVideoShowVv.setBackground(new BitmapDrawable(Resources.getSystem(), bitmap));
            mImgShowIv.setImageBitmap(bitmap);
            mVideoShowVv.start();
        }
    }

    private void showText() {
        File file = new File(getCacheDir(), "hello.txt");
        if (file.exists()) {
            Log.i(TAG, "text exists");
            try {
                InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
                char[] buff = new char[1024];
                int len;
                while ((len = streamReader.read(buff)) != -1) {
                    mTextShowTv.setText(buff, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getAssetsImgFile() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("my_love_idle.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            mImgShowIv.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAssetsTextFile() {
        AssetManager assets = getAssets();
        try {
            InputStream inputStream = assets.open("hello.txt");
            File file = new File(getCacheDir(), "hello.txt");
            Log.i(TAG, "--------------" + getExternalCacheDir());
            Log.i(TAG, "--------------" + getCacheDir());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = inputStream.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, len);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAssetsVideoFile() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("anim.mp4");
            File file = new File(getCacheDir() + File.separator + "videos", " anim.mp4");
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
                file.createNewFile();
            }
            int len;
            byte[] buff = new byte[1024];
            FileOutputStream outputStream = new FileOutputStream(file);
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}