package com.sdkj.mem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdkj.mem.R;
import com.sdkj.mem.activity.DetailActivity;
import com.sdkj.mem.bean.CheckRecord;

import java.util.List;

public class NewsItemAdapter extends BaseAdapter
{

	private LayoutInflater mInflater;
	private List<CheckRecord> mDatas;
	private Context context;
	/**
	 * 使用了github开源的ImageLoad进行了数据加载
	 */
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;

	public NewsItemAdapter(Context context, List<CheckRecord> datas)
	{
		this.context = context;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
//				.showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory()
//				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300))
//				.build();

	}

	public void addAll(List<CheckRecord> mDatas)
	{
		this.mDatas.addAll(mDatas);
	}

	public void setDatas(List<CheckRecord> mDatas)
	{
		this.mDatas.clear();
		this.mDatas.addAll(mDatas);
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.effects_item, null);
			holder = new ViewHolder();

			holder.mName = (TextView) convertView.findViewById(R.id.tv_user_name);
			holder.mEffect = (TextView) convertView.findViewById(R.id.tv_effect);
			holder.mAddress = (TextView) convertView.findViewById(R.id.tv_user_add);
			holder.sbTime = (TextView) convertView.findViewById(R.id.tv_time);
			holder.goCheck = (TextView) convertView.findViewById(R.id.tv_goCheck);

			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		CheckRecord record = mDatas.get(position);
		holder.mName.setText(record.getUserName());
		holder.mEffect.setText(record.getEffect());
		holder.mAddress.setText(record.getAddress());

		holder.goCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DetailActivity.class);
				context.startActivity(intent);
			}
		});
//		holder.mContent.setText(newsItem.getContent());
//		holder.mDate.setText(newsItem.getDate());
//		if (newsItem.getImgLink() != null)
//		{
//			holder.mImg.setVisibility(View.VISIBLE);
//			imageLoader.displayImage(newsItem.getImgLink(), holder.mImg, options);
//		} else
//		{
//			holder.mImg.setVisibility(View.GONE);
//		}

		return convertView;
	}

	private final class ViewHolder
	{
		TextView mName,mEffect,mAddress;
		TextView sbTime;
		TextView goCheck;
		TextView mDate;
	}

}
