package com.vijay.distributed.core.aop;

import java.text.MessageFormat;

import com.vijay.distributed.core.annotation.LoginCheck;
import com.vijay.distributed.core.annotation.RemoveLoginCheck;
import com.vijay.distributed.core.base.BaseFront;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.core.exception.BadRequestException;
import com.vijay.distributed.core.exception.ErrorMsgEnum;
import com.vijay.distributed.core.exception.ErrorMsgException;
import com.vijay.distributed.core.exception.NoAuthException;
import com.vijay.distributed.core.system.SysConfig;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DataAccessException;

import com.alibaba.dubbo.rpc.RpcException;


//声明这是一个组件
//@Component
// 声明这是一个切面Bean
//@Aspect
public class ExceptionHandler extends BaseFront implements ThrowsAdvice {


    // 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点 smsSendValidateCode
    // @Pointcut("execution(public * com.ycmm.business.user.service..*(..)) ")
    // public void aspect() {
    // }

    // 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
    // @Before("aspect()")
    // public void before(JoinPoint joinPoint) {
    // System.out.println("前置通知======== " + joinPoint);
    // }

    // 配置后置通知,使用在方法aspect()上注册的切入点
    // @After("aspect()")
    // public void after(JoinPoint joinPoint) {
    // System.out.println("后置通知=========== " + joinPoint);
    // }

    // 配置环绕通知,使用在方法aspect()上注册的切入点
    // @Around("aspect()")
    public ResultBean around(JoinPoint joinPoint) throws Throwable {
        ParamBean bizParamBean = null;
        ResultBean resultBean = null;
        String className = null;
        String methodName = null;
        Object[] param = null;
        ProceedingJoinPoint proceedingJoinPoint = null;
        try {
            proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
            param = proceedingJoinPoint.getArgs();
            if (param != null && param.length == 1) {
                Object object = param[0];
                bizParamBean = (object instanceof ParamBean) ? (ParamBean) object : null;
            }

            Class<? extends Object> forName = proceedingJoinPoint.getTarget().getClass();
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            if (methodSignature.getMethod().getAnnotation(RemoveLoginCheck.class) == null) {
                LoginCheck annotation = forName.getAnnotation(LoginCheck.class);
                if (annotation == null) {
                    annotation = methodSignature.getMethod().getAnnotation(LoginCheck.class);
                }
                if (annotation != null && bizParamBean != null
                        && (StringUtils.isEmpty(bizParamBean.getSID()) || StringUtils.isEmpty(bizParamBean.getUid()))) {
                    throw new NoAuthException();
                }
            }

            resultBean = (ResultBean) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            if (e instanceof ErrorMsgException) {
                resultBean = new ResultBean((ErrorMsgException) e);
            } else if (e instanceof NoAuthException) {
                resultBean = new ResultBean(ErrorMsgEnum.NoAuthException);
            } else if (e instanceof BadRequestException) {
                resultBean = new ResultBean(ErrorMsgEnum.badRequestException);
            } else if (e instanceof RpcException) {
                resultBean = new ResultBean(new ErrorMsgException(ErrorMsgEnum.ERROR_SERVER, "内部服务提供异常"));
            } else {
                if (e instanceof DataAccessException) {
                    resultBean = new ResultBean(ErrorMsgEnum.ERROR_SERVER, SysConfig.getProperty("name") + " 服务数据库异常");
                } else {
                    resultBean = new ResultBean(ErrorMsgEnum.ERROR_SERVER);
                }
                if (proceedingJoinPoint != null) {
                    Class<? extends Object> forName = proceedingJoinPoint.getTarget().getClass();
                    className = forName.getSimpleName();
                    methodName = proceedingJoinPoint.getSignature().getName();
                }
                if (bizParamBean != null) {
                    String logInfo = MessageFormat.format("[ERROR][{0}][{1}][{2}][{3}][{4}]", bizParamBean.getIp(), className, methodName,
                            bizParamBean.getUid(), bizParamBean.getParam());
                    logger.error(logInfo, e);
                } else {
                    logger.error(JSONArray.fromObject(param), e);
                }
            }
        }
        return resultBean;
    }
    // 配置后置返回通知,使用在方法aspect()上注册的切入点
    // @AfterReturning("aspect()")
    // public void afterReturn(JoinPoint joinPoint) {
    // System.out.println("后置返回通知============ " + joinPoint);
    // }

}
