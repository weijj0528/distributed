package com.weiun.core.aop;

import com.weiun.base.bean.ParamBean;
import com.weiun.base.bean.ResultBean;
import com.weiun.base.exception.NoAuthException;
import com.weiun.core.annotation.LoginCheck;
import com.weiun.core.annotation.RemoveLoginCheck;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * The type Exception handler.
 * 错误切面处理
 *
 * @author weiun
 */
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
            if (e instanceof InvocationTargetException) {
                e = ((InvocationTargetException) e).getTargetException();
            }
            if (proceedingJoinPoint != null) {
                Class<? extends Object> forName = proceedingJoinPoint.getTarget().getClass();
                className = forName.getSimpleName();
                methodName = proceedingJoinPoint.getSignature().getName();
                String logInfo = MessageFormat.format("[{0}][{1}]:{2}",
                        className, methodName, JSONArray.fromObject(param));
                if (bizParamBean != null) {
                    logInfo = MessageFormat.format("[ER][{0}][{1}][{2}][{3}][{4}][{5}][{6}]",
                            bizParamBean.getIp(), className, methodName,
                            bizParamBean.getUid(), bizParamBean.getModel(),
                            bizParamBean.getVersion(), bizParamBean.getParam());
                }
                logger.error(logInfo, e);
            }
            throw e;
        }
        return resultBean;
    }

}
