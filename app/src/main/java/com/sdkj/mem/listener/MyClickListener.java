package com.sdkj.mem.listener;

import android.view.View;

/**
 * Created on 2016-12-12.
 *
 * @author KittyKang
 */
public abstract  class MyClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        onViewClick(v);
    }

    public abstract void onViewClick(View v);
}
