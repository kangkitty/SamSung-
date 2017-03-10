package com.sdkj.mem.utils;

import android.os.Environment;


/**
 * 常量设置类
 * @author Administrator
 *
 */
public class Constant {

	//手指位置跟随图标的坐标信息
	public static final int X_INDEX = 0;
	public static final int Y_INDEX = 1;
	public static final int LOCATE_INFO[][] = {
		{66,55},{94,28},{126,20},{158,27},{190,133},
		{312,133},{345,27},{376,20},{408,28},{434,55}
	};
	
	public static final int MANAGER_LOCATE_INFO[][] = {
		{115,75},{143,45},{175,35},{207,47},{238,145},
		{354,145},{393,47},{425,35},{457,45},{485,75}
	};
	
	//提示信息
	public static final String TIPS[] = {
		"请扫描左手小指静脉三次","请扫描左手无名指静脉三次",
		"请扫描左手中指静脉三次","请扫描左手食指静脉三次",
		"请扫描左手拇指静脉三次","请扫描右手拇指静脉三次",
		"请扫描右手食指静脉三次","请扫描右手中指静脉三次",
		"请扫描右手无名指静脉三次","请扫描右手小指静脉三次"};
	
	public static final String DEL_TIPS[] = {
		"左手小指","左手无名指",
		"左手中指","左手食指",
		"左手大拇指","右手大拇指",
		"右手食指","右手中指",
		"右手无名指","右手小指"
	};
	
	public static final String DETAIL_TIPS[] = {
		"小指","无名指",
		"中指","食指",
		"大拇指","大拇指",
		"食指","中指",
		"无名指","小指"
	};
	
	public static final String EXCEPT_TIPS[] = {
		"左小","左无名",
		"左中","左食",
		"左拇","右拇",
		"右食","右中",
		"右无名","右小"
	};
	
	public static final String EXCEPT_TIPS2[] = {
		"身份证采集异常","身份证认证异常","指静脉采集异常","指静脉认证异常"
	};
	
	public static final String FINGER_TIPS[] = {"01","02","03","04","05","11","12","13","14","15"};
	//指静脉总共扫描次数
	public static final int TOTAL_COUNT = 3;
	
	public static final String SD_PATH = "/mnt/sdcard";
	public static final String USB_PATH = "/mnt/usbotg";
	public static final String Data_PATH = Environment.getExternalStorageDirectory().toString()+"/exam";
	
	//数据格式错误
	public static final int IMPORT_DATA=0x8181;
	public static final int CHECK_DARA = 0x8282;
	public static final int DATA_OK = 0x8383;
	
	
	//http://127.0.0.1:8080/schoolExam/api/exams?devices=123
	//http://127.0.0.1:8080/schoolExam/api/imp?values=1
	
	
	public static final String BASE_URL = "http://192.168.0.189:8080";
	
	//上传考试结果
	public static final String UP_ENROLL = BASE_URL + "/schoolExam/api/impFeature";
	//下载考试数据
	public static final String DOWN_EXAM = BASE_URL +"/schoolExam/api/exams";
	//上传特征数据
	public static final String UP_EXAM = BASE_URL +"/schoolExam/api/impExam";
//	//上传考试结果
//	public static final String UP_ENROLL = "http://192.168.0.189:8080/schoolExam/api/impFeature";
//	//下载考试数据
//	public static final String DOWN_EXAM = "http://192.168.0.189:8080/schoolExam/api/exams";
//	//上传特征数据
//	public static final String UP_EXAM = "http://192.168.0.189:8080/schoolExam/api/impExam";
	
}
