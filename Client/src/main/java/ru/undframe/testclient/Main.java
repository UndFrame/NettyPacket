package ru.undframe.testclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.StringPacket;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws Exception {
        connect();
    }

    private static void connect() throws InterruptedException {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Client client = new Client();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientDecoder(), new ClientEncoder(), new ClientHandler(client));
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
            client.setChannel(channel);
            for (int i = 0; i < 3000; i++) {
                client.sendPacket(new StringPacket("hello world!!!!!"+i)).thenAccept(packet -> {
                    StringPacket stringPacket = (StringPacket)packet;
                    System.out.println(stringPacket.getString());
                });
            }

            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
