package com.yum.oa.common.result;

/**
 * 生成返回结果工具类
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
public class ResultGenerator {

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), data);
    }

    public static ResultBean<?> failed(String msg) {
        return new ResultBean<>(ResultCode.EXPECTATION_FAILED.getCode(), msg, null);
    }

    public static ResultBean<?> failed(ResultCode code) {
        return new ResultBean<>(code.getCode(), code.getMsg(), null);
    }

    public static ResultBean<?> failed(ResultCode code, String msg) {
        return new ResultBean<>(code.getCode(), msg, null);
    }

    public static <T> ResultBean<T> failed(ResultCode code, T data) {
        return new ResultBean<>(code.getCode(), code.getMsg(), data);
    }

    public static <T> ResultBean<T> failed(ResultCode code, String msg, T data) {
        return new ResultBean<>(code.getCode(), msg, data);
    }
}
