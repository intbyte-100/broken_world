package com.intbyte.bw.gameAPI.utils;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataBuffer {
    private final ArrayList<Byte> manifest = new ArrayList<>();
    private final ArrayList<Byte> data = new ArrayList<>();

    private final byte[] from = new byte[4];
    private final byte[] to = new byte[4];

    private static List<Byte> toByteList(byte[] bytes) {
        ArrayList<Byte> arrayList = new ArrayList<>();
        for (byte i : bytes) {
            arrayList.add(i);
        }
        return arrayList;
    }

    public void writeBytes(byte[] key, byte... value) {
        manifest.add((byte) (key.length));
        manifest.addAll(toByteList(key));
        manifest.addAll(toByteList(ByteBuffer.allocate(4).putInt(data.size()).array()));
        manifest.addAll(toByteList(ByteBuffer.allocate(4).putInt(data.size() + value.length).array()));
        data.addAll(toByteList(value));
    }

    public byte[] getBytes(byte[] key) {
        short currentIndex = 0;
        loop:
        while (currentIndex < manifest.size()) {
            byte length = manifest.get(currentIndex);
            if (length != key.length) {
                currentIndex += length + 9;
                continue;
            }

            for (int i = currentIndex + 1; i < currentIndex + length + 1; i++) {
                if (manifest.get(i) != key[i - currentIndex - 1]) {
                    currentIndex += length + 9;
                    continue loop;
                }
            }
            from[0] = manifest.get(currentIndex + length + 1);
            from[1] = manifest.get(currentIndex + length + 2);
            from[2] = manifest.get(currentIndex + length + 3);
            from[3] = manifest.get(currentIndex + length + 4);

            to[0] = manifest.get(currentIndex + length + 5);
            to[1] = manifest.get(currentIndex + length + 6);
            to[2] = manifest.get(currentIndex + length + 7);
            to[3] = manifest.get(currentIndex + length + 8);

            int fromRead = ByteBuffer.wrap(from).getInt(), toRead = ByteBuffer.wrap(to).getInt();
            byte[] bytes = new byte[toRead - fromRead];
            for (int i = fromRead; i < toRead; i++) {
                bytes[i - fromRead] = data.get(i);
            }
            return bytes;
        }
        return null;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[4 + manifest.size() + data.size()];

        int index = 0;
        for (byte i : ByteBuffer.allocate(4).putInt(manifest.size()).array())
            bytes[index++] = i;
        for (; index < manifest.size() + 4; index++)
            bytes[index] = manifest.get(index - 4);
        for (; index < manifest.size() + 4 + data.size(); index++)
            bytes[index] = data.get(index - manifest.size() - 4);
        return bytes;
    }

    public DataBuffer readBytes(byte[] bytes) {
        manifest.clear();
        data.clear();
        from[0] = bytes[0];
        from[1] = bytes[1];
        from[2] = bytes[2];
        from[3] = bytes[3];
        int index = 4;
        int manifestSize = ByteBuffer.wrap(from).getInt();
        int dataSectorSize = bytes.length - 4 - manifestSize;
        for (; index < manifestSize + 4; index++) {
            manifest.add(bytes[index]);
        }

        for (; index < manifestSize + 4 + dataSectorSize; index++)
            data.add(bytes[index]);

        return this;
    }

    public void put(int key, byte[] bytes) {
        writeBytes(ByteBuffer.allocate(4).putInt(key).array(), bytes);
    }

    public void put(int key, byte value) {
        writeBytes(ByteBuffer.allocate(4).putInt(key).array(), value);
    }

    public void put(int key, short value) {
        put(key, ByteBuffer.allocate(2).putShort(value).array());
    }

    public void put(int key, int value) {
        put(key, ByteBuffer.allocate(4).putInt(value).array());
    }

    public void put(int key, long value) {
        put(key, ByteBuffer.allocate(8).putLong(value).array());
    }

    public void put(int key, float value) {
        put(key, ByteBuffer.allocate(4).putFloat(value).array());
    }

    public void put(int key, double value) {
        put(key, ByteBuffer.allocate(8).putDouble(value).array());
    }

    public void put(int key, ExtraData data) {
        put(key, data.getBytes());
    }

    public void put(String key, byte[] data) {
        writeBytes(key.getBytes(), data);
    }

    public void put(String key, short value) {
        put(key, ByteBuffer.allocate(2).putShort(value).array());
    }

    public void put(String key, int value) {
        put(key, ByteBuffer.allocate(4).putInt(value).array());
    }

    public void put(String key, long value) {
        put(key, ByteBuffer.allocate(8).putLong(value).array());
    }

    public void put(String key, float value) {
        put(key, ByteBuffer.allocate(4).putFloat(value).array());
    }

    public void put(String key, double value) {
        put(key, ByteBuffer.allocate(8).putDouble(value).array());
    }

    public void put(String key, ExtraData data) {
        put(key, data.getBytes());
    }

    public byte[] getBytes(int key) {
        return getBytes(ByteBuffer.allocate(4).putInt(key).array());
    }

    public byte getByte(int key) {
        return getBytes(key)[0];
    }

    public short getShort(int key) {
        return ByteBuffer.wrap(getBytes(key)).getShort();
    }

    public int getInt(int key) {
        return ByteBuffer.wrap(getBytes(key)).getInt();
    }

    public long getLong(int key) {
        return ByteBuffer.wrap(getBytes(key)).getLong();
    }

    public float getFloat(int key) {
        return ByteBuffer.wrap(getBytes(key)).getFloat();
    }

    public double getDouble(int key) {
        return ByteBuffer.wrap(getBytes(key)).getDouble();
    }

    public byte[] getBytes(String key) {
        return getBytes(key.getBytes());
    }

    public byte getByte(String key) {
        return getBytes(key)[0];
    }

    public short getShort(String key) {
        return ByteBuffer.wrap(getBytes(key)).getShort();
    }

    public int getInt(String key) {
        return ByteBuffer.wrap(getBytes(key)).getInt();
    }

    public long getLong(String key) {
        return ByteBuffer.wrap(getBytes(key)).getLong();
    }

    public float getFloat(String key) {
        return ByteBuffer.wrap(getBytes(key)).getFloat();
    }

    public double getDouble(String key) {
        return ByteBuffer.wrap(getBytes(key)).getDouble();
    }
}
