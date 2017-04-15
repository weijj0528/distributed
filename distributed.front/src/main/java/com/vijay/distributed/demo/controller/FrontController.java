package com.vijay.distributed.demo.controller;

import com.vijay.distributed.core.base.BaseController;
import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.core.exception.ErrorMsgException;
import com.vijay.distributed.demo.service.FrontService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by weiun on 2017/4/13.
 */
@Controller
@RequestMapping("front")
public class FrontController extends BaseController {

    @Autowired
    FrontService frontService;

    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResultBean request(@RequestBody FrontParamBean frontParamBean) throws Exception {
        if (StringUtils.isEmpty(frontParamBean.getModule()))
            throw new ErrorMsgException("请指定要调用业务模块！");
        if (StringUtils.isEmpty(frontParamBean.getMethod()))
            throw new ErrorMsgException("请指定调用的业务模块接口！");
        return frontService.request(frontParamBean);
    }

}
