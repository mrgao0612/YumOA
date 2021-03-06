package com.yum.oa.common.im.server;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/24
 **/
public final class ChannelSupervise {
    private static final Map<String, Channel> ChannelMap = new ConcurrentHashMap<>(16);

    public static Channel getChannel(String userId) {
        return ChannelMap.get(userId);
    }

    public static void addChannel(String userId, Channel channel) {
        ChannelMap.put(userId, channel);
    }

    public static void removeChannel(Channel channel) {
        ChannelMap.entrySet().stream()
                .filter(entry -> entry.getValue() == channel)
                .forEach(entry -> ChannelMap.remove(entry.getKey()));
    }
}
