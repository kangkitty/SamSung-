package com.sdkj.mem.listener;

import android.widget.TextView;

/**
 * 定义  自定义对话框按钮的点击回调接口
 * @author Administrator
 *
 */
public interface IDialogListener {

	//方法
	void onMyClick(TextView view);
	//删除
	void onDelete(String id, boolean isStart);
}
