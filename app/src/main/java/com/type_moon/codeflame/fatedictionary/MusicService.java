package com.type_moon.codeflame.fatedictionary;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.IOException;

public class MusicService extends Service {
    public static MediaPlayer mp= new MediaPlayer();
    public MusicService() {
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/piano.flac");
            mp.prepare();       //进入就绪状态
            mp.setLooping(true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            switch (code) {
                case 100:
                    if (mp != null) {
                        mp.stop();
                        try {
                            mp.prepare();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 101:
                    if (mp.isPlaying()) {
                        mp.stop();
                    } try {
                        mp.setDataSource(Environment.getExternalStorageDirectory() + "/piano.flac");
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                    break;

                case 102: //退出按钮，服务处理函数
                    if (mp.isPlaying()) {
                        mp.stop();
                    } try {
                        mp.setDataSource(Environment.getExternalStorageDirectory() + "/Unity.mp3");
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                    break;

                case 103: //界面刷新，服务返回数据函数
                    if (mp.isPlaying()) {
                        mp.stop();
                    } try {
                        mp.setDataSource(Environment.getExternalStorageDirectory() + "/melt.mp3");
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}