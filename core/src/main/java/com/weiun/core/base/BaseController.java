package com.weiun.core.base;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.weiun.base.exception.*;
import com.weiun.base.bean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by vijay on 2017/4/13.
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 统一异常处理
     *
     * @param request the request
     * @param ex      the ex
     * @return the api result
     */
    @ResponseBody
    @ExceptionHandler
    public ResultBean exceptionHandler(HttpServletRequest request, Exception ex) {
        String url = request.getRequestURI();
        logger.error(url, ex);
        if (ex instanceof NoAuthException) {
            return new ResultBean(ErrorMsgEnum.NoAuthException);
        } else if (ex instanceof ErrorMsgException) {
            return new ResultBean((ErrorMsgException) ex);
        } else if (ex instanceof OvertimeException) {
            return new ResultBean(ErrorMsgEnum.OvertimeException);
        } else if (ex instanceof UnauthorizedException) {
            return new ResultBean(ErrorMsgEnum.UnauthorizedException);
        } else if (ex instanceof InsufficientBalanceException) {
            return new ResultBean(ErrorMsgEnum.Insufficient_Balance);
        } else if (ex instanceof InvocationTargetException) {
            new ResultBean(new ErrorMsgException("服务正在升级，请稍候再试！"));
        } else if (ex instanceof MySQLSyntaxErrorException) {
            new ResultBean(new ErrorMsgException("数据服务正在升级，请稍候再试！"));
        } else if (ex instanceof RuntimeException) {
            new ResultBean(new ErrorMsgException("程序服务正在升级，请稍候再试！"));
        }
        return new ResultBean(new ErrorMsgException("服务异常，请稍候再试！"));
    }

}
