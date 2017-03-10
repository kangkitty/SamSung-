package com.sdkj.mem.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.classic.common.MultipleStatusView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sdkj.mem.R;
import com.sdkj.mem.adapter.NewsItemAdapter;
import com.sdkj.mem.bean.CheckRecord;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{

	/**
	 * 获得newType
	 *
	 * @param newsType
	 */
	public static final String[] TITLES = new String[] { "待办", "未完成", "完成"};
	private List<CheckRecord> mDatas;
	private List<CheckRecord> okDatas = new ArrayList<CheckRecord>();

	/**
	 * 多视图View
	 */
	private MultipleStatusView mMultipleStatusView;
	private PullToRefreshListView mPullList;
//	private ListView mListView;
	private NewsItemAdapter mAdapter;
	private int type;
	public MainFragment(int nType,List<CheckRecord> datas)
	{
		this.type = nType;
		this.mDatas = datas;
//		Logger.e(newsType + "newsType");
//		mNewsItemBiz = new NewsItemBiz();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.tab_item_fragment_main, container,false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}


	public void initViews(View view){
		mAdapter = new NewsItemAdapter(getActivity(), okDatas);
//		/**
//		 * 初始化
//		 */
//		mListView = (ListView) getView().findViewById(R.id.id_xlistView);
//		mListView.setAdapter(mAdapter);
		mMultipleStatusView = (MultipleStatusView) view.findViewById(R.id.main_multiplestatusview);
		mPullList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		mPullList.setAdapter(mAdapter);
		mMultipleStatusView.showLoading();
	}

	public void initEvents(){
		new Thread(){
			@Override
			public void run() {
				super.run();
				initDatas();
			}
		}.start();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
//		mNewsItemDao = new NewsItemDao(getActivity());

//				initDatas();
//		new Thread(){
//			@Override
//			public void run() {
//				super.run();
//			}
//		}.start();

//		mXListView.setPullRefreshEnable(this);
//		mXListView.setPullLoadEnable(this);
//		mXListView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newsType));

	}


	public void initDatas(){
		okDatas.clear();
		if(mDatas != null && mDatas.size()>0){
			for(int i = 0;i<mDatas.size();i++){
				CheckRecord record = mDatas.get(i);

				if(type == record.getState()){
					okDatas.add(record);
				}
			}

		}

		if(okDatas.size() > 0){
			mMultipleStatusView.showContent();
			mAdapter.notifyDataSetChanged();
		}else{
			mMultipleStatusView.showEmpty();
		}

	}

}
