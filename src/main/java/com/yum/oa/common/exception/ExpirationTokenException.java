package com.yum.oa.common.exception;

/**
 * 过期token异常
 * @description
 * @author: gaoyu
 * @create: 2020-08-06
 * @version: 0.0.1
 **/
public class ExpirationTokenException extends RuntimeException {
    public ExpirationTokenException(String msg) {
        super(msg);
    }
}
