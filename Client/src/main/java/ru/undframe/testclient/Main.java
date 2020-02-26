package ru.undframe.testclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.StringPacket;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
                    ch.pipeline().addLast(new ReplayingDecoder() {
                        @Override
                        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                            ByteBuf byteBuf = in.readSlice(4);
                            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{byteBuf.getByte(0), byteBuf.getByte(1), byteBuf.getByte(2), byteBuf.getByte(3)});
                            out.add(in.readBytes(byteBuffer.getInt()));
                        }
                    }, new MessageToByteEncoder<String>() {
                        @Override
                        protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                            int length = msg.getBytes().length;
                            ByteBuf buffer = ctx.alloc().buffer(length);
                            byte[] array = ByteBuffer.allocate(4).putInt(length).array();
                            buffer.writeBytes(array);
                            buffer.writeBytes(msg.getBytes());
                            out.writeBytes(buffer);
                        }
                    }, new ClientHandler());
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

            for (int i = 0; i < 300; i++) {
                channel.writeAndFlush("Hello world my dear friend "+i);
            }

            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
