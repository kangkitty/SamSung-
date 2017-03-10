package com.sdkj.mem.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * View
 *
 * @author KittyKang
 *
 */
public class ViewUtil {

	//getView(),通过id获取指定View
	public static View getView(Context context,int resId){
		View view = null;
		view = LayoutInflater.from(context).inflate(resId, null);
		return view;
	}

	public static void showCurrentView(View view) {

		view.setVisibility(View.VISIBLE);
	}

	public static void hideCurrentView(View view) {

		view.setVisibility(View.INVISIBLE);
	}

	public static void goneCurrentView(View view) {

		view.setVisibility(View.GONE);
	}
}
