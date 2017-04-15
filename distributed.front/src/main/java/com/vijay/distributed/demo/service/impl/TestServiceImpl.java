package com.vijay.distributed.demo.service.impl;

import com.vijay.distributed.core.annotation.LoginCheck;
import com.vijay.distributed.core.base.BaseService;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.demo.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/15.
 */
@Service()
@LoginCheck
public class TestServiceImpl extends BaseService implements TestService {

    public ResultBean test(ParamBean paramBean) {
        logger.info("test");
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("200");
        resultBean.setMsg("test");
        return resultBean;
    }
}
