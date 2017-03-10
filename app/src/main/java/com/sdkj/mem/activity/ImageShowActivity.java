package com.sdkj.mem.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.polites.android.GestureImageView;
import com.sdkj.mem.R;

import net.bither.util.NativeUtil;

public class ImageShowActivity extends Activity
{

	private Context context;
	private String url;
	private ProgressBar mLoading;
	private GestureImageView mGestureImageView;

	//异步加载图片
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_page);
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
		imageUri = getIntent().getStringExtra("imageUri");
		mGestureImageView = (GestureImageView) findViewById(R.id.image);
		mGestureImageView.setImageBitmap(NativeUtil.getBitmapFromFile(imageUri));
//		imageLoader.displayImage(imageUri,mGestureImageView,options);

	}

	/**
	 * 点击返回按钮
	 *
	 * @param view
	 */
	public void back(View view)
	{
		finish();
	}


}
