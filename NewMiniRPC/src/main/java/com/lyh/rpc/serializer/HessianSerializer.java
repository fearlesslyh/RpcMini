package com.lyh.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author 梁懿豪
 * @version 1.3
 * Hessian序列化器实现类，提供对象的序列化和反序列化功能
 */
public class HessianSerializer implements Serializer {
    @Override
    /**
     * 将指定对象序列化为字节数组
     * @param value 待序列化的对象
     * @return 序列化后的字节数组
     * @throws IOException 序列化过程中出现的异常
     */
    public <T> byte[] serialize(T value) throws IOException {
        // 创建字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建Hessian输出流
        HessianOutput hessianOutput = new HessianOutput(outputStream);
        // 将对象写入Hessian输出流
        hessianOutput.writeObject(value);
        // 刷新Hessian输出流
        hessianOutput.flush();
        // 将字节数组输出流转换为字节数组
        return outputStream.toByteArray();

    }

    @Override
    /**
     * 从字节数组反序列化为指定类型的对象
     * @param bytes 待反序列化的字节数组
     * @param type  目标对象的类
     * @return 反序列化后的对象
     * @throws IOException 反序列化过程中出现的异常
     */
    // <T> 表示这是一个泛型方法，T表示方法的返回类型
    // deserialize 方法用于将字节数组反序列化为指定类型的对象
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        // 创建一个ByteArrayInputStream对象，用于读取字节数组
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        // 创建一个HessianInput对象，用于读取Hessian序列化的对象
        HessianInput hessianInput = new HessianInput(inputStream);
        //noinspection unchecked,将Hessian序列化的对象反序列化为指定类型的对象
        return (T) hessianInput.readObject(type);

    }
}

