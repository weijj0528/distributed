package com.weiun.base.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiun.base.exception.ErrorMsgEnum;
import com.weiun.base.exception.ErrorMsgException;
import com.weiun.base.util.ObjUtils;

import java.io.Serializable;

/**
 * 返回结果封装
 *
 * @author Johnson.Jia
 */
public class ResultBean implements Serializable, Cloneable {
    /**
     * @author Johnson.Jia
     */
    private static final long serialVersionUID = 8632964679110167394L;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误编号
     */
    private String code;
    /**
     * 业务返回值
     */
    private JSONObject result;

    public ResultBean() {
        super();
        this.msg = ErrorMsgEnum.SUCCESS.getMsg();
        this.code = ErrorMsgEnum.SUCCESS.getCode();
        this.result = new JSONObject();
    }

    public ResultBean(ErrorMsgException exception) {
        this.msg = exception.getMsg();
        this.code = exception.getCode();
        this.result = new JSONObject();
    }

    public ResultBean(ErrorMsgEnum errorMsgEnum, String msg) {
        this.msg = msg;
        this.code = errorMsgEnum.getCode();
        this.result = new JSONObject();
    }

    public ResultBean(ErrorMsgEnum msgEnum) {
        this.msg = msgEnum.getMsg();
        this.code = msgEnum.getCode();
        this.result = new JSONObject();
    }

    public ResultBean(Object result) {
        this.msg = ErrorMsgEnum.SUCCESS.getMsg();
        this.code = ErrorMsgEnum.SUCCESS.getCode();
        if (result == null) {
            this.result = new JSONObject();
        } else if (ObjUtils.isArray(result)) {
            JSONObject json = new JSONObject();
            json.put("list", result);
            this.result = json;
        } else if (ObjUtils.isString(result)) {
            this.msg = String.valueOf(result);
            this.result = new JSONObject();
        } else if (ObjUtils.isBoolean(result)) {
            JSONObject json = new JSONObject();
            json.put("status", result);
            this.result = json;
        } else {
            if (result.getClass() == JSONObject.class) {
                this.result = (JSONObject) result;
            } else
                this.result = JSON.parseObject(JSON.toJSONString(result));
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JSONObject getResult() {
        return result;
    }


    public <T> T getResult(Class<T> clazz) {
        return JSON.parseObject(result.toString(), clazz);
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"msg\":\"").append(msg);
        sb.append("\",\"code\":\"").append(code);
        sb.append("\",\"result\":").append(result);
        sb.append("}");
        return sb.toString();
    }


    @Override
    public ResultBean clone() {
        try {
            return (ResultBean) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

}
