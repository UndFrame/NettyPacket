package ru.undframe.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.ndframe.packets.*;

import java.nio.ByteBuffer;
import java.util.List;

public class ServerDecoder extends ReplayingDecoder {

    private PacketFactory packetFactory = new PacketManager();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf byteBuf = in.readSlice(8);
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{byteBuf.getByte(0), byteBuf.getByte(1), byteBuf.getByte(2), byteBuf.getByte(3)});
        out.add(getPacket(in.readBytes(byteBuffer.getInt()),PacketId.of(ByteUtils.byteToInt(
                new byte[]{byteBuf.getByte(4), byteBuf.getByte(5), byteBuf.getByte(6), byteBuf.getByte(7)}
        ))));
    }
    protected Packet getPacket(ByteBuf in, PacketId packetId) { // (2)
        try {
            byte oneHash = in.getByte(0);
            byte twoHash = in.getByte(1);
            byte[] arrsyBytes = new byte[in.capacity()-2];
            for (int i1 = 2; i1 < in.capacity(); i1++) {
                arrsyBytes[i1-2] = in.getByte(i1);
            }
            Packet packet = packetFactory.getPacket(oneHash, twoHash, arrsyBytes);
            packet.setPacketId(packetId);
            return packet;
        }finally {
            in.release();
        }
    }
}
