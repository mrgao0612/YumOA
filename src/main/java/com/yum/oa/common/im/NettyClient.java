package com.yum.oa.common.im;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/22
 **/
@Component
public class NettyClient {
    private EventLoopGroup group = new NioEventLoopGroup();
    private SocketChannel socketChannel;

    private static Logger log = LoggerFactory.getLogger(NettyClient.class);

    private static final String HOST = "localhost";
    private static final int PORT = 8989;

    public void sendMessage(MessageBase.Message message) {
        socketChannel.writeAndFlush(message);
    }

    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST, PORT)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientHanddlerInitializer());
        ChannelFuture future = bootstrap.connect();
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                log.info("连接Netty服务成功");
            } else {
                log.error("连接失败，进行断线重连...");
                future1.channel().eventLoop().schedule(this::start, 20, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();
    }
}
