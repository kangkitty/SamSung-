package com.sdkj.mem;

import android.app.Application;

import com.sdkj.mem.crash.CrashHandler;

public class MyApplication extends Application {
	


	@Override
	public void onCreate() {
		super.onCreate();
		//使用完之后，记得stop
		 CrashHandler crashHandler = CrashHandler.getInstance();
	     crashHandler.init(getApplicationContext());

	}


}
