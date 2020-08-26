package com.yum.oa.common.im.server;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.yum.oa.common.im.protocol.MessageBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * @author gaoyu
 * @version v
 * @description
 * @date 2020/8/24
 **/
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        // 支持参数对象解析， 比如POST参数， 设置聚合内容的最大长度
        pipeline.addLast(new HttpObjectAggregator(65536));
        // 支持大数据流写入
        pipeline.addLast(new ChunkedWriteHandler());
        // 支持WebSocket数据压缩
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // Websocket协议配置， 设置访问路径
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true));
        //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //Google Protocol Buffers 长度属性编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());

        // 协议包解码
        pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> objs) throws Exception {
                if (frame instanceof TextWebSocketFrame) {
                    // 文本消息
                    TextWebSocketFrame textFrame = (TextWebSocketFrame)frame;
                } else if (frame instanceof BinaryWebSocketFrame) {
                    // 二进制消息
                    ByteBuf buf = frame.content();
                    objs.add(buf);
                    // 自旋累加
                    buf.retain();
                }else if (frame instanceof CloseWebSocketFrame) {
                    // 关闭指令消息
                    ch.close();
                }
            }
        });
        // 协议包编码
        pipeline.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
                ByteBuf result = null;
                if (msg instanceof MessageLite) {
                    // 没有build的Protobuf消息
                    result = wrappedBuffer(((MessageLite) msg).toByteArray());
                }
                if (msg instanceof MessageLite.Builder) {
                    // 经过build的Protobuf消息
                    result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
                }

                // 将Protobuf消息包装成Binary Frame 消息
                WebSocketFrame frame = new BinaryWebSocketFrame(result);
                out.add(frame);
            }
        });
        // Protobuf消息解码器
        pipeline.addLast(new ProtobufDecoder(MessageBase.Message.getDefaultInstance()));
        // 自定义数据处理器
        pipeline.addLast(nettyServerHandler);

    }
}
