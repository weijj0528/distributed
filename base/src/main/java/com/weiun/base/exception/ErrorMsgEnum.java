package com.weiun.base.exception;

/**
 * V1  错误枚举列表
 *
 * @author Johnson.Jia
 */
public enum ErrorMsgEnum {

    /**
     * 操作处理成功
     */
    SUCCESS("1c01", "操作处理成功"),

    /**
     * 自定义弹窗处理
     * 操作异常，请稍候重试
     */
    ERROR_ALERT("0e00", "操作异常，请稍候重试"),

    /**
     * 系统错误 请稍候重新尝试
     */
    ERROR_SERVER("0e01", "系统错误,请稍候尝试"),

    /**
     * 错误提示  并进行 自定义跳转处理
     * TODO  跳转参数 待完善
     */
    CUSTOM_JUMP("0e02", "自定义跳转处理"),

    /**
     * 自定义分享处理
     * TODO 分享参数待完善
     */
    CUSTOM_SHARE("0e03", "自定义分享处理"),
    /**
     * 余额不足
     */
    Insufficient_Balance("0e04", "余额不足"),
    /**
     * 用户已在其他设备登录  当前认证无效	   抛出 401
     */
    UnauthorizedException("401", "设备登出"),

    /**
     * 账户 未登录  需要重新登录
     */
    OvertimeException("408", "请求超时"),
    /**
     * 账户 未登录  需要重新登录
     */
    NoAuthException("403", "账户未登录，需要重新登录"),
    /**
     * 错误请求，参数不完整
     */
    badRequestException("400", "错误请求，参数不完整");

    ErrorMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
