package ru.ndframe.packets;

public class StringPacket extends Packet {

    private String s;

    public StringPacket(byte[] bytes) {
        s = new String(bytes);
    }

    public StringPacket(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }

    @Override
    public byte[] toByte() {
        return s.getBytes();
    }

    @Override
    public byte getOneCode() {
        return 0;
    }

    @Override
    public byte getTwoCode() {
        return 1;
    }

    @Override
    public String toString() {
        return super.toString()+" StringPacket{" +
                "s='" + s + '\'' +
                '}';
    }
}
