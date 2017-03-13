package com.sdkj.mem;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created on 2017-02-27.
 *
 * @author KittyKang
 */
public abstract  class BaseActivity  extends FragmentActivity {


    /**
     * 处理与子线程交互的Handler
     * @param msg
     */
    public abstract void handlerMessage(Message msg);


    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在解析数据...");
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
