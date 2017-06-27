package com.sdkj.mem.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.sdkj.mem.BaseActivity;
import com.sdkj.mem.R;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.utils.BitmapUtils;
import com.sdkj.mem.utils.CameraCore;
import com.sdkj.mem.utils.CameraProxy;
import com.sdkj.mem.utils.Constant;
import com.sdkj.mem.utils.FileSizeUtil;
import com.sdkj.mem.utils.ToastUtil;
import com.sdkj.mem.utils.ViewUtil;
import com.sdkj.mem.wheelview.DateUtils;
import com.sdkj.mem.wheelview.JudgeDate;
import com.sdkj.mem.wheelview.ScreenInfo;
import com.sdkj.mem.wheelview.WheelMain;

import net.bither.util.NativeUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2017-03-01.
 * 查看任务状态
 * @author KittyKang
 */
public class TaskDetailActivity extends BaseActivity{

    public static final String TAG = "TaskDetailActivity";
    private Context context;

    //显示View
    private TextView mName,mPhone,mAddress,mEffect,mPXTime,mTestName,mTestResult,mCXName,mCXTime;
    private TextView mPhoneModel,mPhonePart,mBZ;
    private TextView mBack;

    //待填写View
    private ImageView mPhoto;//拍照
    private TextView mCXState;
    private String baseImg = "";



    //接收传递过来的数据
    private CheckRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        this.context = this;
        record = (CheckRecord) getIntent().getSerializableExtra("checkRecord");
        initViews();
        initDatas();
        initEvents();
    }

    public void initViews(){
        mPhoto = (ImageView) findViewById(R.id.iv_photo);

        //显示View,与EditText
        mName = (TextView) findViewById(R.id.tv_name);
        mPhone = (TextView) findViewById(R.id.tv_phone);
        mAddress = (TextView) findViewById(R.id.tv_address);
        mEffect = (TextView) findViewById(R.id.tv_effect_info);
        mPXTime = (TextView) findViewById(R.id.tv_px_time);
        mTestName = (TextView) findViewById(R.id.tv_testName);
        mTestResult = (TextView) findViewById(R.id.tv_testResult);
        mCXName = (TextView) findViewById(R.id.tv_cx_user);
        mCXState = (TextView) findViewById(R.id.tv_cx_state);
        mCXTime = (TextView) findViewById(R.id.tv_cx_time);

        mPhoneModel = (TextView) findViewById(R.id.et_hj_type);
        mPhonePart = (TextView) findViewById(R.id.et_fxh);
        mBZ = (TextView) findViewById(R.id.tv_cx_bz);
        mBack = (TextView) findViewById(R.id.tv_submit);
    }

    public void initDatas(){
        if(!TextUtils.isEmpty(record.getUserName())){
            mName.setText(record.getUserName());
        }
        if(!TextUtils.isEmpty(record.getUserTel())){
            mPhone.setText(record.getUserTel());
        }

        if(!TextUtils.isEmpty(record.getPhoneMobile())){
            mPhoneModel.setText(record.getPhoneMobile());
        }

        if(!TextUtils.isEmpty(record.getPhoneBox())){
            mPhonePart.setText(record.getPhoneBox());
        }

        if(!TextUtils.isEmpty(record.getAddress())){
            mAddress.setText(record.getAddress());
        }

        if(!TextUtils.isEmpty(record.getEffect())){
            mEffect.setText(record.getEffect());
        }

        if(!TextUtils.isEmpty(record.getPxTime())){
            mPXTime.setText(record.getPxTime());
        }

        if(!TextUtils.isEmpty(record.getFaultTestUserName())){
            mTestName.setText(record.getFaultTestUserName());
        }


        if(!TextUtils.isEmpty(record.getFaultTestResult())){
            mTestResult.setText(record.getFaultTestResult());
        }

        if(!TextUtils.isEmpty(record.getResult())){
            mBZ.setText(record.getResult());
        }

        if(!TextUtils.isEmpty(record.getCxUserName())){
            mCXName.setText(record.getCxUserName());
        }
        if(!TextUtils.isEmpty(record.getCxTime())){
            mCXTime.setText(record.getCxTime());
        }

        if(!TextUtils.isEmpty(record.getImgRes())){
            baseImg = record.getImgRes();
            Bitmap bitmap = BitmapUtils.base64ToBitmap(record.getImgRes());
            mPhoto.setImageBitmap(bitmap);

        }

        if(record.getState().equals("3")){
         //未完成
            mCXState.setText("未完成");
        }else if(record.getState().equals("1")){
            mCXState.setText("已完成");
        }else{
            mCXState.setText("待修复");
        }

    }

    public void initEvents(){
        mPhoto.setOnClickListener(mListener);
        mBack.setOnClickListener(mListener);

    }



    @Override
    public void handlerMessage(Message msg) {

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //在ImageShowActivity中显示图片

                Intent intent = null;
            switch (v.getId()){
                case R.id.iv_photo:
                    //点击拍照按钮
                    if(!TextUtils.isEmpty(baseImg)){
                        intent = new Intent(context,ImageShowActivity.class);
                        intent.putExtra("imageUri","");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("checkRecord",record);
                        intent.putExtras(bundle);

                        startActivity(intent);

                    }
                    break;

                case R.id.tv_submit:
                    //点击返回按钮
                    finish();
                    break;
            }
        }
    };

}
