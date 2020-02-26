package ru.undframe.testclient;

import io.netty.channel.Channel;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.PacketId;
import ru.ndframe.packets.StringPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Client {

    private Channel channel;
    private Map<PacketId, CompletableFuture<Packet>> actions = new HashMap<>();


    public CompletableFuture<Packet> sendPacket(Packet packet) {
        Objects.requireNonNull(packet);
        Objects.requireNonNull(channel);
        CompletableFuture<Packet> completableFuture = new CompletableFuture<>();
        packet.setPacketId(PacketId.createNew());
        channel.writeAndFlush(packet);
        actions.put(packet.getPacketId(), completableFuture);
        return completableFuture;
    }

    public CompletableFuture<Packet> getCompletableFuture(PacketId packetId) {
        return actions.getOrDefault(packetId, null);
    }


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
