package com.vijay.distributed.core.bean;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 业务参数封装
 */
public class ParamBean implements Serializable, Cloneable {

    /**
     * 请求版本号
     */
    private String version;

    /**
     * 请求客户端 模型 类型
     */
    private String model;
    /**
     * 用户 session id
     */
    private String SID;
    /**
     * 用户 请求 ip地址
     */
    private String ip;
    /**
     * 用户 uid
     */
    private String uid;
    /**
     * 业务请求参数
     */
    private JSONObject param;

    private static final long serialVersionUID = 1L;

    public ParamBean() {
        super();
    }

    public ParamBean(FrontParamBean frontParamBean) {
        this.SID = frontParamBean.getSID();
        this.uid = frontParamBean.getUid();
        this.param = frontParamBean.getParam();
        this.version = frontParamBean.getVersion();
    }

    public <T> T getParam(Class<T> clazz) {
        return JSON.parseObject(param.toString(), clazz);
    }

    public JSONObject getParam() {
        if (param == null) {
            param = new JSONObject();
        }
        return param;
    }

    public void setBiz_param(JSONObject param) {
        this.param = param;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String sID) {
        SID = sID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"ip\":\"").append(ip);
        sb.append("\",\"model\":\"").append(model);
        sb.append("\",\"version\":\"").append(version);
        sb.append("\",\"uid\":\"").append(uid);
        sb.append("\"}");
        return sb.toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public ParamBean clone() {
        try {
            return (ParamBean) super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}
