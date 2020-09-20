package org.saowu.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;


import java.net.InetSocketAddress;


/**
 * 配置服务器的启动代码，将服务器绑定到它要监听连接请求的端口上
 */
public class BootServer {

    private int port;

    public BootServer() {
        this(9000);
    }

    public BootServer(int port) {
        this.port = port;
        new InitCenter(9000);
    }

    public void start() throws Exception {
        //创建 EventLoopGroup
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            // 创建 ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //http 编解码
                            pipeline.addLast(new HttpServerCodec());
                            //http 消息聚合器
                            pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 1024));
                            //100 Continue
                            pipeline.addLast(new HttpServerExpectContinueHandler());
                            //http 请求处理器
                            pipeline.addLast(new HttpRequestHandler());
                        }
                    });
            //异步地绑定服务器; 调用 sync()方法阻塞 等待直到绑定完成
            ChannelFuture future = serverBootstrap.bind().sync();
            //获取 Channel 的 CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup， 释放所有的资源
            boss.shutdownGracefully().sync();
            work.shutdownGracefully().sync();
        }
    }

}



