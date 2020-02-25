package ru.undframe.testclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.PacketFactory;
import ru.ndframe.packets.PacketManager;

import java.util.List;

public class ClientDecoder extends ByteToMessageDecoder{

    private PacketFactory packetManager = new PacketManager();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        byte oldByte = 0;
        byte newByte= 0;
        for (int i = 0; i < in.capacity(); i ++) {
            oldByte = newByte;
            newByte = in.getByte(i);
            if(newByte == -128 && oldByte == 127){
                ByteBuf byteBuf = in.readSlice(i + 1);
                byte oneHash = byteBuf.getByte(0);
                byte twoHash = byteBuf.getByte(1);
                ByteBuf bytes = byteBuf.slice(2, i - 3);

                byte[] arrsyBytes = new byte[bytes.capacity()];
                for (int i1 = 0; i1 < bytes.capacity(); i1 ++) {
                    arrsyBytes[i1] = bytes.getByte(i1);
                }
                out.add(packetManager.getPacket(oneHash,twoHash,arrsyBytes));
            }
        }
    }
}
