package com.lyh.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author 梁懿豪
 * @version 1.3
 * KryoSerializer类用于实现序列化和反序列化功能，基于Kryo库。
 */
public class KryoSerializer implements Serializer {
    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        // 设置动态序列化和反序列化类，不提前注册所有类（可能有安全问题）
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    /**
     * 将对象序列化为字节数组。
     * @param value 需要序列化的对象
     * @param <T> 对象的类型
     * @return 序列化后的字节数组
     * @throws IOException 可能抛出IO异常
     */
    @Override
    public <T> byte[] serialize(T value) throws IOException {
        // 创建一个ByteArrayOutputStream对象，用于存储序列化后的字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 创建一个Output对象，用于将对象写入ByteArrayOutputStream
        Output output = new Output(byteArrayOutputStream);
        // 使用KRYO_THREAD_LOCAL获取一个Kryo对象，将对象写入Output
        KRYO_THREAD_LOCAL.get().writeObject(output, value);
        // 关闭Output对象
        output.close();
        // 将ByteArrayOutputStream转换为字节数组并返回
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 将字节数组反序列化为指定类型的对象。
     * @param bytes 需要反序列化的字节数组
     * @param type 反序列化后的对象类型
     * @param <T> 对象的类型
     * @return 反序列化后的对象
     * @throws IOException 可能抛出IO异常
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        // 将字节数组转换为输入流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 创建输入流
        Input input = new Input(byteArrayInputStream);
        // 使用KRYO_THREAD_LOCAL获取Kryo对象，并使用该对象将输入流中的对象读取出来
        T result = KRYO_THREAD_LOCAL.get().readObject(input, type);
        // 关闭输入流
        input.close();
        // 返回读取出来的对象
        return result;
    }
}

