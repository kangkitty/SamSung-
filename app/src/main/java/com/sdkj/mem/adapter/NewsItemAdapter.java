package com.sdkj.mem.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
	 * 去维修事件监听器
	 */
	private OnItemSelectListener listener;
	public void setOnItemSelectListener(OnItemSelectListener listener){
		this.listener = listener;
	}
	/**
	 * item监听事件
	 *
	 */
	public interface OnItemSelectListener{
		void onItemClick(View v, CheckRecord record);
		void onItemCheck(View v, CheckRecord record);
	}


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

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
			holder.mPhone = (TextView) convertView.findViewById(R.id.tv_phone);
			holder.sbTime = (TextView) convertView.findViewById(R.id.tv_time);
			holder.goCheck = (TextView) convertView.findViewById(R.id.tv_goCheck);
			holder.itemZone =  convertView.findViewById(R.id.ll_item_zone);

			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final CheckRecord record = mDatas.get(position);
		holder.mName.setText(record.getCxUserName());
		holder.mEffect.setText(record.getEffect());
		holder.mAddress.setText(record.getAddress());
		holder.sbTime.setText(record.getSbTime());
		holder.mPhone.setText(record.getUserTel());

		if(record.getState().equals("1")){
			//已完成
			holder.goCheck.setText("已完成");
			holder.goCheck.setBackground(context.getResources().getDrawable(R.drawable.bg_gray_normal));
		}else if(record.getState().equals("3")){
			//未完成
			holder.goCheck.setText("未完成");
			holder.goCheck.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_blue));
		}else{
			holder.goCheck.setText("去维修");
			holder.goCheck.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_blue));
		}
		if(!record.getState().equals("1")) {
			holder.goCheck.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					if (listener != null) {
						listener.onItemCheck(v, record);
					}

				}
			});
		}

		holder.itemZone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onItemClick(v, record);
				}
			}
		});


		return convertView;
	}

	private final class ViewHolder
	{
		View itemZone;
		TextView mName,mEffect,mAddress,mPhone;
		TextView sbTime;
		TextView goCheck;
		TextView mDate;
	}

}
