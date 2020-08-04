package com.yum.oa.common.result;

import com.alibaba.fastjson.JSON;

/**
 * 返回结果实体
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
public class ResultBean<T> {
    private int code;
    private final String msg;
    private final T data;

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "{\"code\":"+ code + "," +
                "\"msg\":" + msg + "," +
                "\"data\":" + JSON.toJSONString(data) + "}";
    }
}
