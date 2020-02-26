package ru.ndframe.packets;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static Integer byteToInt(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getInt();
    }

    public static byte[] intToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

}
