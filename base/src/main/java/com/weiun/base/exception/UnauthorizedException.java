package com.weiun.base.exception;


/**
 * 用户已在其他设备登录  当前认证无效	   抛出 401
 * @author Johnson.Jia
 * @date 2017年3月30日 下午12:32:11
 */
public class UnauthorizedException extends BaseException {

	private static final long serialVersionUID = 8088934606432865810L;

	@Override
	public String causedBy() {
		return ExceptionCause.unauthorized.name();
	}
}
