package ru.ndframe.packets;

import java.util.Objects;

public class PacketId {

    private static int NUMBER = Integer.MAX_VALUE;

    private int id;

    public PacketId(int id) {
        this.id = id;
    }

    public static PacketId createNew() {

        if(NUMBER--<=Integer.MIN_VALUE)
            NUMBER = Integer.MAX_VALUE;

        return new PacketId(NUMBER);
    }

    public static PacketId of(Integer byteToInt) {
        return new PacketId(byteToInt);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketId packetId = (PacketId) o;
        return id == packetId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PacketId{" +
                "id=" + id +
                '}';
    }
}
