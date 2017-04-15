package com.vijay.distributed.demo.service.impl;

import com.vijay.distributed.core.base.BaseService;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.demo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/15.
 */
@Service
public class DemoServiceImpl extends BaseService implements DemoService {
    public ResultBean demoTest(ParamBean paramBean) {
        logger.info("demoTest");
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("200");
        resultBean.setMsg("demoTest");
        return resultBean;
    }
}
