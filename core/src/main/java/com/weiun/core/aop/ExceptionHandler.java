package com.weiun.core.aop;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.weiun.base.bean.ParamBean;
import com.weiun.base.bean.ResultBean;
import com.weiun.base.exception.*;
import com.weiun.core.annotation.LoginCheck;
import com.weiun.core.annotation.RemoveLoginCheck;
import com.weiun.core.system.SysConfig;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DataAccessException;

import java.text.MessageFormat;

public class ExceptionHandler implements ThrowsAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);


    /**
     * Around result bean.
     *
     * @param joinPoint the join point
     * @return the result bean
     * @throws Throwable the throwable
     */
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
                        && (StringUtils.isEmpty(bizParamBean.getUid()))) {
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
            } else if (e instanceof InsufficientBalanceException) {
                resultBean = new ResultBean(ErrorMsgEnum.Insufficient_Balance);
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
                    logger.error(JSON.toJSONString(param), e);
                }
            }
        }
        return resultBean;
    }

}
