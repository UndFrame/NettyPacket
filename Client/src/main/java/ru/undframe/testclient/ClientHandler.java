package ru.undframe.testclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.ndframe.packets.BytePacket;
import ru.ndframe.packets.Packet;
import ru.ndframe.packets.StringPacket;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof Packet) {
            Packet packet = (Packet)msg;
            client.getCompletableFuture(packet.getPacketId()).complete(packet);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
