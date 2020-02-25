package ru.ndframe.packets;

public interface PacketFactory {

    Packet getPacket(byte oneHash,byte twoHash,byte[] bytes);

}
