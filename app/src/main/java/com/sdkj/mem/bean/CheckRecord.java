package com.sdkj.mem.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2017-02-28.
 *  维修记录单
 * @author KittyKang
 */
public class CheckRecord implements Serializable {

    private String taskid;
    @SerializedName("applyUserName")
    private String userName;
    @SerializedName("applyPhone")
    private String userTel;
    @SerializedName("applyContent")
    private String effect;//故障内容
    @SerializedName("applyAddress")
    private String address;
    @SerializedName("applyTime")
    private String sbTime;
    @SerializedName("serviceRepairTime")
    private String cxTime;
    @SerializedName("serviceTime")
    private String pxTime;
    @SerializedName("serviceRepairState")
    private String state;//订单状态 3 待办 2 未完成  1 完成

    @SerializedName("serviceRepairResult")
    private String result;
    @SerializedName("serviceRepairPhonePartBox")
    private String phoneBox;//分线盒

    @SerializedName("serviceRepairPhoneModel")
    private String phoneMobile;//话机型号
    @SerializedName("serviceCheckUserId")
    private String cxUserId;//查修人员id
    @SerializedName("serviceUserId_name")
    private String cxUserName;//查修人员名字
    @SerializedName("serviceUserId")
    private String pxUserId;
    private String pxName;
    private String faultTestResult;//测试结论
    private String faultTestUserName;//测试人员名字


    @SerializedName("img_serviceRepairSignature")
    private String imgRes;//签字图片


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSbTime() {
        return sbTime;
    }

    public void setSbTime(String sbTime) {
        this.sbTime = sbTime;
    }

    public String getPxTime() {
        return pxTime;
    }

    public void setPxTime(String pxTime) {
        this.pxTime = pxTime;
    }

    public String getCxTime() {
        return cxTime;
    }

    public void setCxTime(String cxTime) {
        this.cxTime = cxTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPxName() {
        return pxName;
    }

    public void setPxName(String pxName) {
        this.pxName = pxName;
    }


    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }


    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getPhoneBox() {
        return phoneBox;
    }

    public void setPhoneBox(String phoneBox) {
        this.phoneBox = phoneBox;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getCxUserId() {
        return cxUserId;
    }

    public void setCxUserId(String cxUserId) {
        this.cxUserId = cxUserId;
    }

    public String getCxUserName() {
        return cxUserName;
    }

    public void setCxUserName(String cxUserName) {
        this.cxUserName = cxUserName;
    }

    public String getPxUserId() {
        return pxUserId;
    }

    public void setPxUserId(String pxUserId) {
        this.pxUserId = pxUserId;
    }

    public String getFaultTestResult() {
        return faultTestResult;
    }

    public void setFaultTestResult(String faultTestResult) {
        this.faultTestResult = faultTestResult;
    }

    public String getFaultTestUserName() {
        return faultTestUserName;
    }

    public void setFaultTestUserName(String faultTestUserName) {
        this.faultTestUserName = faultTestUserName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
