package com.yum.oa.common.utils;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/21
 **/
public final class ObjectUtil {
    /**
     * java类转Map
     * description:
     * @param clazz
     * @return:
     * @author: gaoyu
     * @date: 2020/8/21
     * @version
     */
    public static <T> Map<String, Object> objectTurn2Map(T clazz) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(clazz);
        return jsonObject.getInnerMap();
    }
}
