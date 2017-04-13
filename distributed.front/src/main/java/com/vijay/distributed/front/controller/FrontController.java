package com.vijay.distributed.front.controller;

import com.vijay.distributed.core.base.BaseController;
import com.vijay.distributed.core.bean.FrontParamBean;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.front.service.FrontService;
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
@RequestMapping("/")
public class FrontController extends BaseController {

    @Autowired
    FrontService frontService;

    @ResponseBody
    @RequestMapping(value = "/front", method = RequestMethod.POST)
    public ResultBean request(@RequestBody FrontParamBean frontParamBean) throws Exception {
        return frontService.request(frontParamBean);
    }

}
