package com.sdkj.mem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sdkj.mem.adapter.TabAdapter;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.utils.ScreenUtil;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String[] CHANNELS = new String[]{"待办", "未完成", "完成"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<CheckRecord> mRecords = new ArrayList<CheckRecord>();
    private TabAdapter mAdapter ;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        initDatas();
//        mAdapter = new TabAdapter(getSupportFragmentManager(),mDataList,mRecords);
//        mViewPager.setAdapter(mAdapter);
//        initMagicIndicator();
        ScreenUtil.getScreenDetail(this);

    }

    public void initDatas(){
        //存储数据
        for (int i = 0;i<50;i++){
            CheckRecord record = new CheckRecord();
            record.setUserName("张小" + i);
            record.setEffect("电话打不出去" + i);
            record.setAddress("山西省太原市小店区环湖中路沿街北巷无名路" + i + "号");
            if(i<20){
                record.setState(0);
            }else if(i>40){
                record.setState(2);
            }else{
                record.setState(1);
            }

            mRecords.add(record);
        }

        mAdapter = new TabAdapter(getSupportFragmentManager(),mDataList,mRecords);
        mViewPager.setAdapter(mAdapter);
        initMagicIndicator();

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
}
