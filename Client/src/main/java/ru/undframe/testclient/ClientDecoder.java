package ru.undframe.testclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.PacketFactory;
import ru.ndframe.packets.PacketManager;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ClientDecoder extends ReplayingDecoder {

    private PacketFactory packetFactory = new PacketManager();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf byteBuf = in.readSlice(4);
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{byteBuf.getByte(0), byteBuf.getByte(1), byteBuf.getByte(2), byteBuf.getByte(3)});
        out.add(getPacket(in.readBytes(byteBuffer.getInt())));
    }
    protected Packet getPacket(ByteBuf in) { // (2)
        try {
            byte oneHash = in.getByte(0);
            byte twoHash = in.getByte(1);
            byte[] arrsyBytes = new byte[in.capacity()-2];
            for (int i1 = 2; i1 < in.capacity(); i1++) {
                arrsyBytes[i1-2] = in.getByte(i1);
            }
            return packetFactory.getPacket(oneHash, twoHash, arrsyBytes);
        }finally {
            in.release();
    }
    }
}
