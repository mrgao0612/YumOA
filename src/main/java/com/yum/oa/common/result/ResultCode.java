package com.yum.oa.common.result;

/**
 * 返回结果枚举类
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
public enum ResultCode {
    OK(200, "请求成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "请求未通过身份验证"),
    FORBIDDEN(403, "请求拒绝访问"),
    NOT_FOUND(404, "请求未找到"),
    EXPECTATION_FAILED(417, "请求失败"),
    INTERNAL_ERROR(500, "服务器内部错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
