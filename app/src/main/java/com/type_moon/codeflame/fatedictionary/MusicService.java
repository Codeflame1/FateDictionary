package com.type_moon.codeflame.fatedictionary;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    public final IBinder binder = new MyBinder();
    class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    public static MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void play1() {
        mediaPlayer.stop();
        try { if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
        }
            //重置
            mediaPlayer.reset();
            //加载多媒体文件
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/FateDictionary/quanyutianxia.mp3");
            //准备播放音乐
            mediaPlayer.prepare();
            //播放音乐
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play2() {

        mediaPlayer.stop();
        try { if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
        }
            //重置
            mediaPlayer.reset();
            //加载多媒体文件
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/FateDictionary/quanyutianxia.mp3");
            //准备播放音乐
            mediaPlayer.prepare();
            //播放音乐
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pausePlay() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer.start();
        //重复播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        //播放错误的处理
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //释放资源
                mp.release();
                return false;
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止并释放资源
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
