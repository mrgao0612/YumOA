package com.yum.oa.common.exception;

import com.yum.oa.common.result.ResultBean;
import com.yum.oa.common.result.ResultCode;
import com.yum.oa.common.result.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @description
 * @author: gaoyu
 * @create: 2020-08-07
 * @version: 0.0.1
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NullPointerException.class)
    public ResultBean<?> nullPointerExceptionHandler(NullPointerException e) {
        logger.error("请求接口异常，原因：", e);
        return ResultGenerator.failed();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error("请求参数验证异常，原因：", e);
        return ResultGenerator.failed(ResultCode.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
