package com.sdkj.mem.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * 测量屏幕分辨率、DPI
 * @author Administrator
 *
 */
public class ScreenUtil {

	public static void getScreenDetail(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
		int height = wm.getDefaultDisplay().getHeight();

		DisplayMetrics dm = new DisplayMetrics();
		dm=context.getResources().getDisplayMetrics();
		int densityDPI = dm.densityDpi;//屏幕密度

		LogUtils.e("kitty", "真实分辨率：" + width + "x" + height + "dpi=" + densityDPI);
		ToastUtil.toast(context, "真实分辨率：" + width + "x" + height + "dpi=" + densityDPI);


	}


	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
