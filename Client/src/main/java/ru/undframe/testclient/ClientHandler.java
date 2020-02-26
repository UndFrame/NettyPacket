package ru.undframe.testclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.StringPacket;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof StringPacket){
            StringPacket stringPacket = (StringPacket)msg;
            System.out.println(stringPacket.getString());
        }else  if(msg instanceof BytePacket){
            BytePacket stringPacket = (BytePacket)msg;
           // System.out.println(Arrays.toString(stringPacket.toByte()));
        }else  if(msg instanceof ByteBuf){
            ByteBuf byteBuf = (ByteBuf)msg;
            byte[] bytes = new byte[byteBuf.capacity()];
                for (int i = 0; i < byteBuf.capacity(); i++) {
                    bytes[i] = byteBuf.getByte(i);
                }
                System.out.println(new String(bytes));
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
