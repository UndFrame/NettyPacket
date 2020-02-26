package ru.ndframe.packets;

public abstract class Packet {

    private PacketId packetId;
    public abstract byte[] toByte();
    public abstract byte getOneCode();
    public abstract byte getTwoCode();

    public PacketId getPacketId(){
        return packetId;
    }

    public void setPacketId(PacketId packetId) {
        this.packetId = packetId;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetId=" + packetId +
                '}';
    }
}
