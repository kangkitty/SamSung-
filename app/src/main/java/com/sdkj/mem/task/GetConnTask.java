package com.sdkj.mem.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 读取文件信息
 * Created by KittyKang
 */
public class GetConnTask implements  Runnable {

    public static final String TAG = "GetConnTask";
    private Handler mHandler;
    private int what;

    private Context context;
    private String orgid;
    private String np;
    private int pageIndex;
    private int pageCount;


    public GetConnTask(Context context, Handler handler, int what, String orgid, String np,int page,int count){
        this.context = context;
        this.mHandler = handler;
        this.what = what;
        this.orgid = orgid;
        this.np = np;
        this.pageIndex = page;
        this.pageCount = count;

    }


    @Override
    public void run() {
        //执行耗时操作占位,请求网络；
        String obj = "";
//       if(NetUtil.checkNet(context)){


        switch (what){
//            case ConstantValues.GET_CONN:
//                //获取具体文章信息
//               obj =  postGetConnInfo();
//                break;
//            case ConstantValues.GET_DEPART:
//                obj = postGetDepartInfo();
//                break;
//
//            case ConstantValues.GET_CONN_LIST:
//                obj = postGetConnList();
//                break;
            default:
                break;
        }
//       }else{
////           obj = ConstantValues.NETWORK_ERROR;
//       }
        if(mHandler != null){
            //向主线程发送消息
            Message msg = Message.obtain();
            msg.what = what;
            msg.obj = obj;
            mHandler.sendMessage(msg);
        }
    }


    /**
     * 获取所有联系人信息
     */

//    private String postGetConnList() {
//        try {
//            Response response;
//
//                response = OkGo.get(ConstantValues.CONNLIST)
//                        .tag(this)
//                        .params("login_user_id", orgid)
//                        .execute();
//
//            if(response.isSuccessful()){
//                return response.body().string();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ConstantValues.POST_ERROR;
//    }


}
