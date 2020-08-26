package com.yum.oa.common.im.server;

import com.alibaba.fastjson.JSONObject;
import com.yum.oa.common.im.protocol.MessageBase;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/24
 **/
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<MessageBase.Message> {

    @Resource
    private TaskExecutor taskExecutor;

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageBase.Message message) {
        taskExecutor.execute(() -> {
            switch (message.getCmd()) {
                case NORMAL:
                    log.info("收到客户端发来的业务消息，{}", message.toString());
                    String receiverId = JSONObject.parseObject(message.getContent()).get("receiverId").toString();
                    Channel channel = ChannelSupervise.getChannel(receiverId);
                    channel.writeAndFlush(message);
                    break;
                case HEARTBEAT_REQUEST:
                    log.info("收到客户端发来的心跳消息，{}", message.toString());
                    // 回应pong
                    ctx.writeAndFlush(MessageBase.Message
                            .newBuilder()
                            .setCmd(MessageBase.Message.CommandType.HEARTBEAT_RESPONSE)
                            .setRequestId(ctx.channel().id().asShortText())
                            .setContent("pong").build());
                    break;
                case OPEN:
                    log.info("收到客户端登录成功消息，{}", message.toString());
                    String userId = message.getRequestId();
                    ChannelSupervise.addChannel(userId, ctx.channel());
                    break;
                default:
                    log.warn("消息格式不符合要求");
                    break;
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("客户端加入连接，{}", ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.info("客户端断开连接，{}", ctx.channel().id().asShortText());
        ChannelSupervise.removeChannel(ctx.channel());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("websocket异常：{}",cause.getMessage());
        // 删除通道
        ChannelSupervise.removeChannel(ctx.channel());
        ctx.close();
    }

}
