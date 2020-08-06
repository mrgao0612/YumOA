package com.yum.oa.common.redis;

/**
 * Redis Key 生成类
 * @description
 * @author: gaoyu
 * @create: 2020-08-06
 * @version: 0.0.1
 **/
public class RedisKeyGenerator {
    /**
     * 生成用户鉴权token key
     * @param userId 用户Id
     * @return String
     * @author gaoyu
     * @date 2020/8/6
     * @version 0.0.1
     */
    public static String getUserToken(long userId) {
        return String.format("user:token:%s", userId);
    }
}
