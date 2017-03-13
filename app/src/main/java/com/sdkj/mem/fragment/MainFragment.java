package com.sdkj.mem.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.classic.common.MultipleStatusView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sdkj.mem.R;
import com.sdkj.mem.activity.DetailActivity;
import com.sdkj.mem.activity.TaskDetailActivity;
import com.sdkj.mem.adapter.NewsItemAdapter;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.utils.Constant;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{

	/**
	 * 获得newType
	 *
	 * @param newsType
	 */
	public static final String[] TITLES = new String[] { "2", "3", "1"};
	private List<CheckRecord> mDatas;
	private List<CheckRecord> okDatas = new ArrayList<CheckRecord>();

	/**
	 * 多视图View
	 */
	private MultipleStatusView mMultipleStatusView;
	private PullToRefreshListView mPullList;
//	private ListView mListView;
	private NewsItemAdapter mAdapter;
	private String type;

//	public MainFragment(){
//
//	}
	public MainFragment(int nType,List<CheckRecord> datas)
	{
		this.type = TITLES[nType];
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
		new Thread(){
			@Override
			public void run() {
				super.run();
				initDatas();
			}
		}.start();
		initEvents();
	}


	public void initViews(View view){
		mAdapter = new NewsItemAdapter(getActivity(), okDatas);

		mMultipleStatusView = (MultipleStatusView) view.findViewById(R.id.main_multiplestatusview);
		mPullList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		mPullList.setAdapter(mAdapter);
		mMultipleStatusView.showLoading();
	}

	public void initEvents(){


		mAdapter.setOnItemSelectListener(new NewsItemAdapter.OnItemSelectListener() {
			@Override
			public void onItemClick(View v, CheckRecord record) {
//				点击Item
				Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkRecord",record);
				intent.putExtras(bundle);
				getActivity().startActivityForResult(intent, Constant.REQUEST_OK);
			}

			@Override
			public void onItemCheck(View v, CheckRecord record) {
				//点击去维修
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkRecord",record);
				intent.putExtras(bundle);
				getActivity().startActivityForResult(intent, Constant.REQUEST_OK);
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

	}


	public void initDatas(){
		okDatas.clear();
		if(mDatas != null && mDatas.size()>0){
			for(int i = 0;i<mDatas.size();i++){
				CheckRecord record = mDatas.get(i);

				if(type.equals(record.getState())){
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
