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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.sdkj.mem.BaseActivity;
import com.sdkj.mem.R;
import com.sdkj.mem.utils.CameraCore;
import com.sdkj.mem.utils.CameraProxy;
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

    //待填写View
    private TextView mCXTime;//查修时间
    private ImageView mPhoto;//拍照
    private View mPhotoDel;//删除拍照按钮

    private RadioGroup mRG;//完成单选按钮
    private RadioButton mWC,mWWC;

    private WheelMain wheelMainDate;//日期时间滚轮选择
    private String beginTime;

    //拍照处理
    private CameraProxy cameraProxy;
    private String fileDir = "";
    private String imageUri = "";
    /** SD卡根目录 */
    private final String externalStorageDirectory = Environment.getExternalStorageDirectory().getPath()+"/atest/picture/";

    //异步加载图片
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.context = this;
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
        initEvents();
    }

    public void initViews(){
        mCXTime = (TextView) findViewById(R.id.tv_cx_time);
        mPhoto = (ImageView) findViewById(R.id.iv_photo);
        mPhotoDel = findViewById(R.id.ll_photo_del);
        mRG = (RadioGroup) this.findViewById(R.id.jl_rg);
        mWC = (RadioButton) this.findViewById(R.id.wc_rb);
        mWWC = (RadioButton) this.findViewById(R.id.wwc_rb);


    }

    public void initEvents(){
        mCXTime.setOnClickListener(mListener);
        mPhoto.setOnClickListener(mListener);
        mPhotoDel.setOnClickListener(mListener);

        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == mWC.getId()){
                    ToastUtil.toast(context,mWC.getText().toString());
//                    seletedTv.setText(defaultStr + manRb.getText().toString());
                }else if(checkedId == mWWC.getId()){
                    ToastUtil.toast(context,mWWC.getText().toString());
//                    seletedTv.setText(defaultStr + womanRb.getText().toString());
                }
            }
        });
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
        File file = new File(filePath);
        if (file.exists()) {
            new Thread(){
                public void run() {
                    final File file2 = new File(externalStorageDirectory+"/temp.jpg");

                    NativeUtil.compressBitmap(BitmapFactory.decodeFile(filePath),file2.getPath());

                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageUri = file2.getPath();
//                            imageLoader.displayImage(imageUri,mPhoto,options);
                            ViewUtil.showCurrentView(mPhotoDel);
                            mPhoto.setImageBitmap(NativeUtil.getBitmapFromFile(file2.getPath()));
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
                case  R.id.tv_cx_time:
                    //选择查修时间
                    showBottoPopupWindow();

                    break;
                case R.id.iv_photo:
                    //点击拍照按钮
                    if(!TextUtils.isEmpty(imageUri)){
                        //在ImageShowActivity中显示图片
                        intent = new Intent(context,ImageShowActivity.class);
                        intent.putExtra("imageUri", imageUri);
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
                    ViewUtil.goneCurrentView(mPhotoDel);
                    break;
            }
        }
    };
}
