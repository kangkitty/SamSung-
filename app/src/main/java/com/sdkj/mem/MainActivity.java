package com.sdkj.mem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdkj.mem.adapter.TabAdapter;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.listener.MyClickListener;
import com.sdkj.mem.utils.ButtonUtils;
import com.sdkj.mem.utils.Constant;
import com.sdkj.mem.utils.FileSizeUtil;
import com.sdkj.mem.utils.LogUtils;
import com.sdkj.mem.utils.ScreenUtil;
import com.sdkj.mem.utils.StringEmpty;
import com.sdkj.mem.utils.ToastUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";
    private Context context;
    private static final String[] CHANNELS = new String[]{"待办", "未完成", "完成"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<CheckRecord> mRecords = new ArrayList<CheckRecord>();
    private String mSDCard = Constant.Data_PATH;
    private TabAdapter mAdapter ;
    private ViewPager mViewPager;

    private TextView mRead;

    private Handler handler = new Handler(){

        public void handleMessage(Message msg) {

//			if(msg.what==0x11){
//				updateViews();
//			}

            switch (msg.what) {

                case Constant.DATA_OK:
                    //导入数据成功
                    mAdapter = new TabAdapter(getSupportFragmentManager(),mDataList,mRecords);
                    mViewPager.setAdapter(mAdapter);
                    initMagicIndicator();
                    dismissLoading();
                    break;

                case Constant.CHECK_DARA:
                    ToastUtil.toast(context, Constant.DATA_ERROR);
                    break;

                case Constant.IMPORT_DATA:
                    ToastUtil.toast(context, Constant.NO_DATA);
                    break;

                default:
                    break;
            }

           dismissLoading();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRead = (TextView) findViewById(R.id.tv_read);
//        initDatas();
//        mAdapter = new TabAdapter(getSupportFragmentManager(),mDataList,mRecords);
//        mViewPager.setAdapter(mAdapter);
//        initMagicIndicator();

        if(savedInstanceState != null){
            mAdapter = (TabAdapter) savedInstanceState.getSerializable("select");
            mViewPager.setAdapter(mAdapter);
            initMagicIndicator();
        }
//        ScreenUtil.getScreenDetail(this);
        mRead.setOnClickListener(mListener);
        showLoading("正在解析数据...");
        new Thread(){
            @Override
            public void run() {
                initDatas();
            }
        }.start();

    }

    public void initDatas(){
        //存储数据
//        for (int i = 0;i<50;i++){
//            CheckRecord record = new CheckRecord();
//            record.setUserName("张小" + i);
//            record.setEffect("电话打不出去" + i);
//            record.setAddress("山西省太原市小店区环湖中路沿街北巷无名路" + i + "号");
//            if(i<20){
//                record.setState(0);
//            }else if(i>40){
//                record.setState(2);
//            }else{
//                record.setState(1);
//            }
//
//            mRecords.add(record);
//        }

        //从文件读取json数据，并封装解析
        Message msg = new Message();
        String jsonString = FileSizeUtil.ReadTxtFile(mSDCard+"wq.json");
        if(!jsonString.equals("-1")){

            if(StringEmpty.isGoodJson(jsonString)){

                Type listType = new TypeToken<LinkedList<CheckRecord>>(){}.getType();
                Gson gson = new Gson();
                // LinkedList<ExamInfo> exams = gson.fromJson(jsonString, listType);
                List<CheckRecord> lists = gson.fromJson(jsonString, listType);
                if(lists.size()>0){
                    CheckRecord record = lists.get(0);
                    if(!TextUtils.isEmpty(record.getTaskid())){
                        for (int j= 0;j<lists.size();j++){
                            CheckRecord record2 = lists.get(j);
                            if(TextUtils.isEmpty(record2.getState())){
                                record2.setState("2");
                            }
                        }
                        mRecords.clear();
                        mRecords.addAll(lists);
                        msg.what=Constant.DATA_OK;
                        handler.sendMessage(msg);
                    }else{
                        msg.what=Constant.CHECK_DARA;
                        handler.sendMessage(msg);
                        LogUtils.e(TAG, Constant.DATA_ERROR);
                    }
                }

            }else{
                msg.what=Constant.CHECK_DARA;
                handler.sendMessage(msg);
                LogUtils.e(TAG, Constant.DATA_ERROR);
            }

        }else{
            //没有找到文件
            msg.what=Constant.IMPORT_DATA;
            handler.sendMessage(msg);
            LogUtils.e(TAG, Constant.NO_DATA);
        }

//        mAdapter = new TabAdapter(getSupportFragmentManager(),mDataList,mRecords);
//        mViewPager.setAdapter(mAdapter);
//        initMagicIndicator();

    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.TRANSPARENT);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.gray));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.dark_black));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.high_yellow));
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(MainActivity.this, 80);
            }
        });
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void handlerMessage(Message msg) {

    }


    private MyClickListener mListener = new MyClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()){
                case R.id.tv_read:
                    if (!ButtonUtils.isFastDoubleClick(v.getId())) {
                        showLoading("正在解析数据");
                        new Thread(){
                            @Override
                            public void run() {
                                initDatas();
                            }
                        }.start();
                    }else{
                        ToastUtil.toast(context, "点太快了，要给点反应时间呀");
                    }

                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Constant.REQUEST_OK && resultCode == Constant.RESPONSE_OK){

            //更新数据
            showLoading("正在解析数据...");
            new Thread(){
                @Override
                public void run() {
                    initDatas();
                }
            }.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private long exitTime = 0;

    /**
     * back键再按一次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("select", mAdapter);
    }
}
