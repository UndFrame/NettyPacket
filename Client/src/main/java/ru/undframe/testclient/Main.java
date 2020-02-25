package ru.undframe.testclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.StringPacket;

public class Main {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port =8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientEncoder(),new ClientDecoder(),new ClientHandler());
                }
            });
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            f.awaitUninterruptibly();
            assert f.isDone();

            if (f.isCancelled()) {

            } else if (!f.isSuccess()) {
                f.cause().printStackTrace();
            } else {
                    //TODO
            }
            Channel channel = f.channel();
            channel.writeAndFlush(new StringPacket("hello world my fried"));
            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
