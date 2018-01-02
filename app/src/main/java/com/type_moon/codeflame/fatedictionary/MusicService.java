package com.type_moon.codeflame.fatedictionary;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    public static MediaPlayer mp= new MediaPlayer();

    public final IBinder binder = new MyBinder();
    class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicService() {
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/piano.flac");
            mp.prepare();       //进入就绪状态
            mp.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void choice0 () {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void choice1() {
        if (mp != null) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(Environment.getExternalStorageDirectory() + "/piano.flac");
                mp.prepare();
                mp.setLooping(true);
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void choice2() {
        if (mp != null) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(Environment.getExternalStorageDirectory() + "/Unity.mp3");
                mp.prepare();
                mp.setLooping(true);
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void choice3() {
        if (mp != null) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(Environment.getExternalStorageDirectory() + "/melt.mp3");
                mp.prepare();
                mp.setLooping(true);
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
}