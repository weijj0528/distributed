package com.weiun.base.exception;




/**
 * 用户未登录  抛出 403
 */
public class NoAuthException extends BaseException {
	private static final long serialVersionUID = 8199514192443835496L;

	@Override
	public String causedBy() {
		return ExceptionCause.noAuth.name();
	}

}
