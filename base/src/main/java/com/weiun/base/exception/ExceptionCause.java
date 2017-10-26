package com.weiun.base.exception;

/**
 *  异常处理 枚举类
 */
public enum ExceptionCause {
	//无授权 没有登录					403
	noAuth, 
	//坏的请求 错误的请求  参数不完整	  	400
	badRequest,
	// 用户在其他设备登录  当前认证无效	  	401
	unauthorized,
	//请求超时 						408
	overtime, 
	//自定义错误异常					200
	ErrorMessages;

}
