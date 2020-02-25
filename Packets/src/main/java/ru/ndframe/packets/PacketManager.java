package ru.ndframe.packets;

import java.util.Arrays;

public class PacketManager implements PacketFactory {


    @Override
    public Packet getPacket(byte oneHash,byte twoHash,byte[] bytes) {
        if(oneHash== 0 && twoHash == 0)
            return new BytePacket(bytes);
        if(oneHash== 0 && twoHash == 1)
            return new StringPacket(bytes);

        return null;
    }
}
