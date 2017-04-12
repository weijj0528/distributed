//package com.vijay.distributed.core.exception;
//
//import java.text.MessageFormat;
//
//import net.sf.json.JSONArray;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.aop.ThrowsAdvice;
//import org.springframework.dao.DataAccessException;
//
//import com.alibaba.dubbo.rpc.RpcException;
//
////声明这是一个组件
////@Component
//// 声明这是一个切面Bean
////@Aspect
//public class ExceptionHandler implements ThrowsAdvice {
//
//	public static Logger logger = Logger.getLogger(ExceptionHandler.class);
//
//	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点 smsSendValidateCode
////	@Pointcut("execution(public * com.ycmm.business.user.service..*(..)) ")
////	public void aspect() {
////	}
//
//	// 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
//	// @Before("aspect()")
//	// public void before(JoinPoint joinPoint) {
//	// System.out.println("前置通知======== " + joinPoint);
//	// }
//
//	// 配置后置通知,使用在方法aspect()上注册的切入点
//	// @After("aspect()")
//	// public void after(JoinPoint joinPoint) {
//	// System.out.println("后置通知=========== " + joinPoint);
//	// }
//
//	// 配置环绕通知,使用在方法aspect()上注册的切入点
////	@Around("aspect()")
//	public ResultBean around(JoinPoint joinPoint) throws Throwable {
//		BizParamBean bizParamBean = new BizParamBean();
//		ResultBean resultBean = null;
//		String className = null;
//		String methodName = null;
//		Object[] param = null;
//		ProceedingJoinPoint proceedingJoinPoint = null;
//		try {
//			proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
//			param = proceedingJoinPoint.getArgs();
//			if (param != null && param.length == 1 ) {
//				Object object = param[0];
//				bizParamBean = (object instanceof BizParamBean) ? (BizParamBean) object : null;
//			}
//
////			UserLoginCheck annotation = forName.getAnnotation(UserLoginCheck.class);
////			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
////			if (annotation == null) {
////				annotation = methodSignature.getMethod().getAnnotation(UserLoginCheck.class);
////			}
////			if (annotation != null) {
////				RemoveLoginCheck loginCheck = methodSignature.getMethod().getAnnotation(RemoveLoginCheck.class);
////				if(loginCheck == null && bizParamBean != null){
////					if (bizParamBean.getSID() == null
////							|| bizParamBean.getUid() == null) {
////						throw new NoAuthException();
////					}
////				}
////			}
//
//			resultBean = (ResultBean) proceedingJoinPoint.proceed();
//		} catch (Throwable e) {
//			if (e instanceof ErrorMsgException) {
//				resultBean = new ResultBean((ErrorMsgException) e);
//			} else if (e instanceof NoAuthException) {
//				resultBean = new ResultBean(ErrorMsgEnum.NoAuthException);
//			} else if (e instanceof BadRequestException) {
//				resultBean = new ResultBean(ErrorMsgEnum.badRequestException);
//			} else if(e instanceof RpcException){
//				resultBean = new ResultBean(new ErrorMsgException(ErrorMsgEnum.ERROR_SERVER,"内部服务提供异常"));
//			} else {
//				if(e instanceof DataAccessException){
//					resultBean = new ResultBean(ErrorMsgEnum.ERROR_SERVER,SysConfig.sysConfig.getProperty("name")+" 服务数据库异常");
//				}else{
//					resultBean = new ResultBean(ErrorMsgEnum.ERROR_SERVER);
//				}
//				if(proceedingJoinPoint != null){
//					Class<? extends Object> forName = proceedingJoinPoint.getTarget().getClass();
//					className = forName.getSimpleName();
//					methodName = proceedingJoinPoint.getSignature().getName();
//				}
//				if(bizParamBean != null){
//					String logInfo = MessageFormat.format(Consts.LOGGER_ERROR_FORMAT,bizParamBean.getIp(),className,methodName,
//							bizParamBean.getUid(),bizParamBean.getBiz_param());
//					logger.error(logInfo,e);
//				}else{
//					logger.error(JSONArray.fromObject(param),e);
//				}
//			}
//		}
//		return resultBean;
//	}
//
//	// 配置后置返回通知,使用在方法aspect()上注册的切入点
//	// @AfterReturning("aspect()")
//	// public void afterReturn(JoinPoint joinPoint) {
//	// System.out.println("后置返回通知============ " + joinPoint);
//	// }
//}
