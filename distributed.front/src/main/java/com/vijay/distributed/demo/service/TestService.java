package com.vijay.distributed.demo.service;

import com.vijay.distributed.core.annotation.LoginCheck;
import com.vijay.distributed.core.bean.ParamBean;
import com.vijay.distributed.core.bean.ResultBean;

/**
 * Created by Administrator on 2017/4/15.
 */
@LoginCheck
public interface TestService {

    ResultBean test(ParamBean paramBean);

}
