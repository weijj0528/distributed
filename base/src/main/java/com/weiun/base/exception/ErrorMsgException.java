package com.weiun.base.exception;

import org.apache.commons.lang.StringUtils;

/**
 * 返回自定义错误信息 错误问题
 * 
 * @author Johnson.Jia
 */
public class ErrorMsgException extends BaseException {

	private static final long serialVersionUID = 1452976038790619628L;

	/**
	 * 错误 信息
	 */
	private String msg;
	/**
	 * 错误 编码
	 */
	private String code;

	public ErrorMsgException() {
	}

	/**
	 * 创建 ERROR_ALERT 0e00
	 *  错误提示异常 只是用来展示
	 * @author Johnson.Jia
	 * @param msg	错误 信息
	 */
	public ErrorMsgException(String msg) {
		this.code = ErrorMsgEnum.ERROR_ALERT.getCode();
		this.msg = msg;
		if (StringUtils.isBlank(msg)) {
			this.msg = ErrorMsgEnum.ERROR_ALERT.getMsg();
		}
	}

	/**
	 * @author Johnson.Jia
	 */
	public ErrorMsgException(ErrorMsgEnum errorMsgEnum) {
		this.msg = errorMsgEnum.getMsg();
		this.code = errorMsgEnum.getCode();
	}
	/**
	 * @author Johnson.Jia
	 * @param msg 	错误 信息
	 */
	public ErrorMsgException(ErrorMsgEnum errorMsgEnum,String msg) {
		this.msg = msg;
		this.code = errorMsgEnum.getCode();
	}

	/**
	 * @author Johnson.Jia
	 * @param msg 	错误 信息
	 * @param code 	错误 编码
	 */
	public ErrorMsgException(String msg, String code) {
		this.msg = msg;
		this.code = code;
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
