package com.vijay.distributed.core.base;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.vijay.distributed.core.bean.ResultBean;
import com.vijay.distributed.core.exception.ErrorMsgException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by vijay on 2017/4/13.
 */
public class BaseController extends BaseFront {

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
        logger.error(ex);
        String url = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (ex instanceof ErrorMsgException) {
            ErrorMsgException errorMsgException = (ErrorMsgException) ex;
            return new ResultBean(errorMsgException);
        } else if (ex instanceof MySQLSyntaxErrorException) {

        } else if (ex instanceof RuntimeException) {

        }
        return new ResultBean(new ErrorMsgException("服务异常，攻城师们正在紧急处理中……"));
    }

}
