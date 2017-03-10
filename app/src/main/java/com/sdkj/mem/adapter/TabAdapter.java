package com.sdkj.mem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.fragment.MainFragment;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter
{

	private List<String> mDataList;
	private List<CheckRecord> records;
	public TabAdapter(FragmentManager fm,List<String> dataList,List<CheckRecord> records)
	{
		super(fm);
		mDataList = dataList;
		this.records = records;
	}

	@Override
	public Fragment getItem(int arg0)
	{
		MainFragment fragment = new MainFragment(arg0,records);
		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return mDataList.get(position);
	}

	@Override
	public int getCount()
	{
		return mDataList == null ? 0 : mDataList.size();
	}


}
