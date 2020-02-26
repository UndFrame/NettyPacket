package ru.undframe.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.ndframe.packets.PacketFactory;
import ru.ndframe.packets.PacketManager;

import java.nio.charset.Charset;
import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder{

    private PacketFactory packetManager = new PacketManager();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
            System.out.println("decode");
            byte oldByte = 0;
            byte newByte = 0;

            int size = 0;
            for (int i = 0; i < in.capacity(); i++) {
                oldByte = newByte;
                newByte = in.getByte(i);
                System.out.print(newByte+" : ");
                if (newByte == -128 && oldByte == 127) {
                    System.out.print(" {"+size+"} ");
                    ByteBuf byteBuf = in.readSlice(size + 1);
                    byte oneHash = byteBuf.getByte(0);
                    byte twoHash = byteBuf.getByte(1);
                    ByteBuf bytes = byteBuf.slice(2, size - 3);
                    byte[] arrsyBytes = new byte[bytes.capacity()];
                    for (int i1 = 0; i1 < bytes.capacity(); i1++) {
                        arrsyBytes[i1] = bytes.getByte(i1);
                    }
                    out.add(packetManager.getPacket(oneHash, twoHash, arrsyBytes));
                    size = -1;
                }
                size++;
            }
    }
}
