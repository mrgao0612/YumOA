package com.yum.oa.common.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/22
 **/
@Component
public class NettyServer {
    @Resource
    private NettyServerHandlerInitializer nettyServerHandlerInitializer;

    // boss 线程组用于处理连接工作
    private final EventLoopGroup boss = new NioEventLoopGroup();
    // work 线程组用于数据处理
    private final EventLoopGroup work = new NioEventLoopGroup();

    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    /**
     * 启动NettyServer
     * @throws InterruptedException
     */
    @PostConstruct()
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                // 指定channel
                .channel(NioServerSocketChannel.class)
                // 使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(8989))
                // 服务端可连接队列数，对应TCP/IP协议listen函数中backlog参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 设置TCP长连接，一般如果两个小时没有数据通讯时，TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 将小的数据包包装成更大的帧进行传送，提高网络的负载，即TCP延迟传输
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(nettyServerHandlerInitializer);
        ChannelFuture future = bootstrap.bind(8989).sync();
        if (future.isSuccess()) {
            log.info("NettyServer启动成功");
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        log.info("关闭NettyServer");
    }
}
