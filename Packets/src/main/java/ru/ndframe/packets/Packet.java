package ru.ndframe.packets;

public interface Packet {

    byte[] toByte();
    byte getOneCode();
    byte getTwoCode();

}
