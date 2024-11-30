package com.lyh.rpc.serializer;

import java.io.*;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class JDKserializer implements Serializer {

    @Override
    public <T> byte[] serialize(T value) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(value);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            objectInputStream.close();
        }
    }
}
