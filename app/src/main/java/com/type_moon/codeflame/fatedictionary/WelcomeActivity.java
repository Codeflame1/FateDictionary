package com.type_moon.codeflame.fatedictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动界面
        setContentView(R.layout.open_ad);
        ImageView ad = findViewById(R.id.welcome_ad);
        Random random = new Random();
        int i = random.nextInt(3);
        if (i==0) {
            ad.setImageResource(R.mipmap.ad1);
        } else if (i==1) {
            ad.setImageResource(R.mipmap.ad2);
        } else {
            ad.setImageResource(R.mipmap.ad3);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时任务，比如加载网络数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跳转至 MainActivity
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        //结束当前的 Activity
                        WelcomeActivity.this.finish();
                    }
                });
            }
        }).start();
    }
}
