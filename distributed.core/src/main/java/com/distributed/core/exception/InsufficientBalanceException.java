package com.distributed.core.exception;

/**
 * 余额不足错误
 */
public class InsufficientBalanceException extends BaseException {

    private static final long serialVersionUID = 1452976038790619628L;

    /**
     * 错误 信息
     */
    private String msg;
    /**
     * 错误 编码
     */
    private String code;


    public InsufficientBalanceException() {
        this.msg = ErrorMsgEnum.Insufficient_Balance.getMsg();
        this.code = ErrorMsgEnum.Insufficient_Balance.getCode();
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

    @Override
    public String toString() {
        return "ErrorMsgException [msg=" + msg + ", code=" + code + "]";
    }

    @Override
    public String causedBy() {
        return ExceptionCause.ErrorMessages.name();
    }
}
