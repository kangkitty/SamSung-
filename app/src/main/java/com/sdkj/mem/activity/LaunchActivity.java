package com.sdkj.mem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sdkj.mem.BaseActivity;
import com.sdkj.mem.MainActivity;
import com.sdkj.mem.R;

/**
 * Created on 2017-03-12.
 *
 * @author KittyKang
 */
public class LaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动图片
        setContentView(R.layout.activity_launch);
        //后台处理耗时任务
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //耗时任务，比如加载网络数据
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                        //跳转至 MainActivity
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        //结束当前的 Activity
                        LaunchActivity.this.finish();
                    }
                }, 2000);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //跳转至 MainActivity
//                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        //结束当前的 Activity
//                        LaunchActivity.this.finish();
//                    }
//                });
//            }
//        }).start();
    }

    @Override
    public void handlerMessage(Message msg) {

    }
}
