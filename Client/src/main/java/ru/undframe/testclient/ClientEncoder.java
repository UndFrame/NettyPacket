package ru.undframe.testclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.ndframe.packets.ByteUtils;
import ru.ndframe.packets.Packet;

import java.nio.ByteBuffer;

public class ClientEncoder extends MessageToByteEncoder<Packet> {
    @Override
    public void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out)
            throws Exception {
        byte[] bytes = msg.toByte();
        ByteBuf buffer = ctx.alloc().buffer(bytes.length+4+4);
        byte[] array = ByteBuffer.allocate(4).putInt(bytes.length+2).array();
        buffer.writeBytes(array);
        buffer.writeBytes(ByteUtils.intToByte(msg.getPacketId().getId()));
        buffer.writeByte(msg.getOneCode());
        buffer.writeByte(msg.getTwoCode());
        buffer.writeBytes(bytes);
        out.writeBytes(buffer);
    }
}
