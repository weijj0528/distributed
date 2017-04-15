package com.vijay.distributed.demo.service.impl;

import com.vijay.distributed.core.base.BaseService;
import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.core.exception.ErrorMsgException;
import com.vijay.distributed.core.spring.SpringInit;
import com.vijay.distributed.demo.service.DemoService;
import com.vijay.distributed.demo.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by vijay on 2017/4/13.
 */
@Service
public class FrontServiceImpl extends BaseService implements FrontService {


    public ResultBean request(FrontParamBean frontParamBean) throws Exception {
        Object service = SpringInit.getBean(frontParamBean.getModule().trim());
        if (service == null)
            service = SpringInit.getBean(frontParamBean.getModule().trim() + "Impl");
        if (service == null)
            throw new ErrorMsgException("该服务：" + frontParamBean.getModule() + "暂未提供！");
        Method method = null;
        try {
            method = service.getClass().getMethod(frontParamBean.getMethod().trim(), ParamBean.class);
        } catch (Exception e) {
            logger.error(e);
        }
        if (method == null)
            throw new ErrorMsgException("该服务：" + frontParamBean.getModule() + "暂未提供：" + frontParamBean.getMethod() + "接口！");
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
        logger.info("loginCheck");
    }
}
