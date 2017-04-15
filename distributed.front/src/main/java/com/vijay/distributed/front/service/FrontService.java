package com.vijay.distributed.front.service;

import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ResultBean;

/**
 * Created by vijay on 2017/4/13.
 */
public interface FrontService {

    ResultBean request(FrontParamBean frontParamBean) throws Exception;

}
