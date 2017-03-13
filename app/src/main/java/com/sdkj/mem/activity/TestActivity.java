package com.sdkj.mem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sdkj.mem.R;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.utils.BitmapUtils;

/**
 * Created on 2017-03-13.
 *
 * @author KittyKang
 */
public class TestActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
       CheckRecord record = (CheckRecord) getIntent().getSerializableExtra("checkRecord");
        imageView.setImageBitmap(BitmapUtils.base64ToBitmap(record.getImgRes()));
    }
}
