package com.distributed.core.bean;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 前端请求参数封装
 */
public class FrontParamBean implements Serializable, Cloneable {

    /**
     * 请求业务 模块
     */
    private String module;
    /**
     * 请求业务 方法
     */
    private String method;
    /**
     * 客户端 版本号
     */
    private String version;
    /**
     * 用户 session id
     */
    private String SID;
    /**
     * 请求客户端 唯一标识
     */
    private String dev;
    /**
     * 用户 uid
     */
    private String uid;
    /**
     * 请求访问时间戳
     */
    private Long time;
    /**
     * 请求签名 效验
     */
    private String sign;

    private static final long serialVersionUID = 1L;

    /**
     * 业务请求参数
     */
    private JSONObject param;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String sID) {
        SID = sID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"dev\":\"").append(dev);
        sb.append("\",\"module\":\"").append(module);
        sb.append("\",\"method\":\"").append(method);
        sb.append("\",\"time\":").append(time);
        sb.append("}");
        return sb.toString();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    @Override
    public FrontParamBean clone() {
        try {
            return (FrontParamBean) super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}
