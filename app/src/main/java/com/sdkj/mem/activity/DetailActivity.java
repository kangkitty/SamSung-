package com.sdkj.mem.activity;

import android.app.ActionBar;
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
 * @author KittyKang
 */
public class DetailActivity extends BaseActivity implements CameraCore.CameraResult {

    public static final String TAG = "DetailActivity";
    private Context context;

    //显示View
    private TextView mName,mPhone,mAddress,mEffect,mPXTime,mTestName,mTestResult,mCXName;
    private EditText mPhoneModel,mPhonePart,mBZ;

    //待填写View
    private TextView mCXTime;//查修时间
    private ImageView mPhoto;//拍照
    private View mPhotoDel;//删除拍照按钮

    private RadioGroup mRG;//完成单选按钮
    private RadioButton mWC,mWWC;

    private TextView mSubmit;

    private WheelMain wheelMainDate;//日期时间滚轮选择
    private String beginTime;

    //拍照处理
    private CameraProxy cameraProxy;
    private String fileDir = "";
    private String imageUri = "";
    private String baseImg = "";
    /** SD卡根目录 */
    private final String externalStorageDirectory = Environment.getExternalStorageDirectory().getPath()+"/atest/picture/";

    //异步加载图片
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    //接收传递过来的数据
    private CheckRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.context = this;
        record = (CheckRecord) getIntent().getSerializableExtra("checkRecord");
        // imageLoader初始化
        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        options = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();//设置fadein会有闪烁效果

        //APP缓存目录 拍照缓存
        fileDir = getExternalCacheDir().getPath();
        //压缩后保存临时文件目录
        File tempFile = new File(externalStorageDirectory);
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }
        cameraProxy = new CameraProxy(this, DetailActivity.this);
        initViews();
        initDatas();
        initEvents();
    }

    public void initViews(){
        mPhoto = (ImageView) findViewById(R.id.iv_photo);
        mPhotoDel = findViewById(R.id.ll_photo_del);
        mRG = (RadioGroup) this.findViewById(R.id.jl_rg);
        mWC = (RadioButton) this.findViewById(R.id.wc_rb);
        mWWC = (RadioButton) this.findViewById(R.id.wwc_rb);

        //显示View,与EditText
        mName = (TextView) findViewById(R.id.tv_name);
        mPhone = (TextView) findViewById(R.id.tv_phone);
        mAddress = (TextView) findViewById(R.id.tv_address);
        mEffect = (TextView) findViewById(R.id.tv_effect_info);
        mPXTime = (TextView) findViewById(R.id.tv_px_time);
        mTestName = (TextView) findViewById(R.id.tv_testName);
        mTestResult = (TextView) findViewById(R.id.tv_testResult);
        mCXName = (TextView) findViewById(R.id.tv_cx_user);
        mSubmit = (TextView) findViewById(R.id.tv_submit);



        mPhoneModel = (EditText) findViewById(R.id.et_hj_type);
        mPhonePart = (EditText) findViewById(R.id.et_fxh);
        mBZ = (EditText) findViewById(R.id.tv_cx_bz);
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

        if(!TextUtils.isEmpty(record.getImgRes())){
            baseImg = record.getImgRes();
            Bitmap bitmap = BitmapUtils.base64ToBitmap(record.getImgRes());
            mPhoto.setImageBitmap(bitmap);
            ViewUtil.showCurrentView(mPhotoDel);

        }

        if(record.getState().equals("3")){
         //未完成
            mRG.check(mWWC.getId());
        }else{
            record.setState("1");
        }

    }

    public void initEvents(){
        mPhoto.setOnClickListener(mListener);
        mPhotoDel.setOnClickListener(mListener);
        mSubmit.setOnClickListener(mListener);

        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == mWC.getId()){
//                    ToastUtil.toast(context,mWC.getText().toString());
                    record.setState("1");
//                    seletedTv.setText(defaultStr + manRb.getText().toString());
                }else if(checkedId == mWWC.getId()){
                    record.setState("3");
//                    ToastUtil.toast(context,mWWC.getText().toString());
                }
            }
        });

        //为edittext添加监听
        TextChange textChange=new TextChange();
        mPhoneModel.addTextChangedListener(textChange);
        mPhonePart.addTextChangedListener(textChange);
        mBZ.addTextChangedListener(textChange);

        mPhoneModel.setSelection(mPhoneModel.getText().toString().length());//光标在最后位置
        mPhonePart.setSelection(mPhonePart.getText().toString().length());//光标在最后位置
        mBZ.setSelection(mBZ.getText().toString().length());//光标在最后位置
    }


    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void showBottoPopupWindow() {
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window,null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        wheelMainDate.screenheight = screenInfoDate.getHeight();
        String time = DateUtils.currentMonth().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelMainDate.initDateTimePicker(year, month, day, hours,minute);
        final String currentTime = wheelMainDate.getTime().toString();
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(mCXTime, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(0.6f);
        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
        tv_pop_title.setText("选择时间");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                beginTime = wheelMainDate.getTime().toString();
                try {
                    Date begin = dateFormat.parse(currentTime);
                    Date end = dateFormat.parse(beginTime);
                    mCXTime.setText(DateUtils.formateStringH(beginTime, DateUtils.yyyyMMddHHmm));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
    }


    @Override
    public void handlerMessage(Message msg) {

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 拍照成功回调
     */
    @Override
    public void onSuccess(final String filePath) {
        final int photoDegree = NativeUtil.readPictureDegree(filePath);
        File file = new File(filePath);
        if (file.exists()) {
            showLoading("正在压缩图片...");
            new Thread(){
                public void run() {
                    final File file2 = new File(externalStorageDirectory+"/temp.jpg");

//                    NativeUtil.compressBitmap(BitmapFactory.decodeFile(filePath),file2.getPath());

                    NativeUtil.compressBitmap(BitmapFactory.decodeFile(filePath),file2.getPath(),photoDegree);

                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageUri = file2.getPath();
//                            imageLoader.displayImage("file://"+imageUri,mPhoto,options);//有缓存，不能实时刷新

                            ViewUtil.showCurrentView(mPhotoDel);
                            mPhoto.setImageBitmap(NativeUtil.getBitmapFromFile(file2.getPath()));
                            dismissLoading();
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * 拍照失败回调
     * @param message
     */
    @Override
    public void onFail(String message) {
        ToastUtil.toast(context,"照片处理失败");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        cameraProxy.onResult(requestCode, resultCode, data);
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
                Intent intent = null;
            switch (v.getId()){
//                case  R.id.tv_cx_time:
//                    //选择查修时间
//                    showBottoPopupWindow();
//
//                    break;
                case R.id.tv_submit:
                    //提交按钮
                    if(mRG.getCheckedRadioButtonId() == mWC.getId()){

                        NormalDialogStyleTwo();
                    }else{
                        gotoSubmit();
                    }
                    break;
                case R.id.iv_photo:
                    //点击拍照按钮
                    if(!TextUtils.isEmpty(imageUri) || mPhotoDel.getVisibility() == View.VISIBLE){
                        //在ImageShowActivity中显示图片
                        intent = new Intent(context,ImageShowActivity.class);
                        intent.putExtra("imageUri", imageUri);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("checkRecord", record);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        //拍照
                        cameraProxy.getPhoto2Camera(fileDir + "/temp.jpg");
                    }
                    break;
                case R.id.ll_photo_del:

                    //删除拍照按钮
                    mPhoto.setImageResource(R.drawable.ic_no_photo);
                    imageUri = "";
                    baseImg = "";
                    ViewUtil.goneCurrentView(mPhotoDel);
                    break;
            }
        }
    };


    //EditText的监听器
    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mPhoneModel.length()>0){
                record.setPhoneMobile(mPhoneModel.getText().toString());

            }

            if(mPhonePart.length()>0){
                record.setPhoneBox(mPhonePart.getText().toString());
            }

            if(mBZ.length()>0){
                record.setResult(mBZ.getText().toString());
            }


        }
    }


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    public void setBasIn(BaseAnimatorSet bas_in) {
        this.mBasIn = bas_in;
    }

    public void setBasOut(BaseAnimatorSet bas_out) {
        this.mBasOut = bas_out;
    }
    private void NormalDialogStyleTwo() {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content("提交之后的记录不可更改")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .btnText("取消","确定")
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        //取消

                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        //确定
                        gotoSubmit();
                        dialog.dismiss();
                    }
                });

    }


    //提交数据
    public void gotoSubmit(){
        showLoading("正在提交数据");
        if(!TextUtils.isEmpty(imageUri)){
            record.setImgRes(BitmapUtils.bitmapToBase64(NativeUtil.getBitmapFromFile(imageUri)));
        }else{
            record.setImgRes(baseImg);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = df.format(new Date());
        record.setCxTime(today);
        Gson gson = new Gson();
        String jsonString = gson.toJson(record);
        new Thread(){
            @Override
            public void run() {
                final boolean isOk =  FileSizeUtil.saveUserInfo(record, "wq.json");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isOk){
                            dismissLoading();
                            ToastUtil.toast(context,"数据提交成功");
                            Intent intent = new Intent();
                            setResult(Constant.RESPONSE_OK,intent);
                            finish();
                        }else{
                            dismissLoading();
                            ToastUtil.toast(context,"数据提交失败");
                        }
                    }
                });

            }
        }.start();
//        boolean isOk =  FileSizeUtil.saveUserInfo(record, "wq.json");
//        if(isOk){
//            ToastUtil.toast(context,"数据提交成功");
//            Intent intent = new Intent();
//            setResult(Constant.RESPONSE_OK,intent);
//            finish();
//        }else{
//            dismissLoading();
//            ToastUtil.toast(context,"数据提交失败");
//        }
    }
}
