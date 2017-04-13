package com.vijay.distributed.front.service;

import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import org.springframework.stereotype.Service;

/**
 * Created by vijay on 2017/4/13.
 */
@Service
public interface FrontService {

    ResultBean request(FrontParamBean frontParamBean) throws Exception;

}
