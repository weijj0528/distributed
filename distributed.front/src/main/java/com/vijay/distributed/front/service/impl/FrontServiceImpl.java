package com.vijay.distributed.front.service.impl;

import com.vijay.distributed.core.annotation.LoginCheck;
import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.core.exception.ErrorMsgException;
import com.vijay.distributed.core.spring.SpringInit;
import com.vijay.distributed.front.service.FrontService;

import java.lang.reflect.Method;

/**
 * Created by vijay on 2017/4/13.
 */
public class FrontServiceImpl implements FrontService {

    public ResultBean request(FrontParamBean frontParamBean) throws Exception {
        Object service = SpringInit.getBean(frontParamBean.getModule().trim());
        if (service == null)
            throw new ErrorMsgException("该服务：" + frontParamBean.getModule() + "暂未提供！");
        LoginCheck annotation = service.getClass().getAnnotation(LoginCheck.class);
        if (annotation != null) {
            loginCheck(frontParamBean);
        }
        Method method = service.getClass().getMethod(frontParamBean.getMethod().trim(), ParamBean.class);
        if (method == null)
            throw new ErrorMsgException("该服务：" + frontParamBean.getModule() + "暂未提供：" + frontParamBean.getMethod() + "接口！");
        annotation = method.getAnnotation(LoginCheck.class);
        if (annotation != null) {
            loginCheck(frontParamBean);
        }
        ParamBean paramBean = new ParamBean(frontParamBean);
        try {
            Object result = method.invoke(service, paramBean);
            return (ResultBean) result;
        } catch (Exception e) {
            throw new ErrorMsgException("服务正在升级，请稍候重试！");
        }
    }

    private void loginCheck(FrontParamBean frontParamBean) throws Exception {
        // 登录检查
    }
}
