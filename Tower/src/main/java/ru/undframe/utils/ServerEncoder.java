package ru.undframe.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.ndframe.packets.Packet;

public class ServerEncoder extends MessageToByteEncoder<Packet> {
    @Override
    public void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out)
            throws Exception {
        System.out.println("encode");
        byte[] bytes = msg.toByte();
        ByteBuf buffer = ctx.alloc().buffer(bytes.length + 2);
        buffer.writeByte(msg.getOneCode());
        buffer.writeByte(msg.getTwoCode());
        buffer.writeBytes(bytes);
        buffer.writeByte(127);
        buffer.writeByte(-128);
        out.writeBytes(buffer);
    }
}
