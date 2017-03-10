package com.sdkj.mem.task;

import android.os.Handler;
import android.os.Message;

import com.sdkj.mem.BaseActivity;

import java.lang.ref.WeakReference;


/**
 * Created by KittyKang on 2016-10-21.
 */
public class BaseHandler extends Handler {

    private WeakReference<BaseActivity> reference;

    public BaseHandler(BaseActivity activity) {
        reference = new WeakReference<BaseActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (reference != null && reference.get() != null) {
            reference.get().handlerMessage(msg);
        }
    }

}
