package ru.ndframe.packets;

public class BytePacket extends Packet {

    private byte[] bytes;

    public BytePacket(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] toByte() {
        return bytes;
    }

    @Override
    public byte getOneCode() {
        return 0;
    }

    @Override
    public byte getTwoCode() {
        return 0;
    }

}
