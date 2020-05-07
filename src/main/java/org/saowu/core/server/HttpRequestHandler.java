package org.saowu.core.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.saowu.core.utils.HttpRequestUtils;
import org.saowu.core.utils.ReflexUtils;
import org.saowu.core.utils.ResponseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        //解析请求
        String request_type = req.method().name();
        Map<String, Object> params = new HashMap<>();
        String uri = HttpRequestUtils.parseRequestData(req, params);
        //打印日志
        System.err.println("Netty Server : " + new SimpleDateFormat("yyy-MM-dd hh:mm:ss").format(new Date()) + " " + request_type + " -> " + uri);
        //路由分配返回数据
        Object result = ReflexUtils.call(uri, request_type, params);
        // 封装http响应
        FullHttpResponse response = ResponseUtils.getFullHttpResponse(result);
        // 将http响应write到客户端
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 通知ChannelInboundHandler最后一次对channelRead()
     * 的调用是当前批量读取中的最后一条消息
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 在读取操作期间，有异常抛出时会关闭该Channel
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}