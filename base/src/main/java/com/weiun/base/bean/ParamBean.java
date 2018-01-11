package com.weiun.base.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 业务参数封装
 */
public class ParamBean implements Serializable, Cloneable {
    /**
     * 请求模块
     */
    private String module;
    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求版本号
     */
    private String version;
    /**
     * 时间戳字符串
     */
    private String time;
    /**
     * 设备ID
     */
    private String devices;
    /**
     * 签名
     */
    private String sign;

    /**
     * 请求客户端 模型 类型
     */
    private String model;

    /**
     * 用户 请求 ip地址
     */
    private String ip;

    /**
     * 用户 session-id
     */
    private String sid;

    /**
     * 用户 uid
     */
    private String uid;

    /**
     * 用户 身份
     */
    private String identity;

    /**
     * 业务请求参数
     */
    private JSONObject param;

    private static final long serialVersionUID = 1L;

    public ParamBean() {
        super();
    }

    public <T> T getParam(Class<T> clazz) {
        return param.toJavaObject(clazz);
    }

    public JSONObject getParam() {
        if (param == null) {
            param = new JSONObject();
        }
        return param;
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

    @Override
    public ParamBean clone() {
        try {
            return (ParamBean) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime() {
        return time;
    }

}
