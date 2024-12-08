package com.lyh.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;

import java.io.IOException;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class JsonSerializer implements Serializer {
    // 创建一个ObjectMapper对象
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T value) throws IOException {
        // 将对象序列化为字节数组
        return objectMapper.writeValueAsBytes(value);
    }
    //JSON序列化器的实现相对复杂，要考虑一些对象转换的兼容性问题，比如Object数组在序列化后会丢失类型。
    //所以在这里对丢失类型的进行了反序列化的处理
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        // 将字节数组反序列化为对象
        T obj = objectMapper.readValue(bytes, type);
        // 如果对象是RpcRequest类型，则调用handleRequest方法处理
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, type);
        }
        // 如果对象是RpcResponse类型，则调用handleResponse方法处理
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, type);
        }
        return obj;
    }

    // 处理RpcResponse对象

    /**
     * 进行了两次序列化反序列化操作，响应和请求进行的配对类型的处理，都是对data值，参数值操作，使之变成正确的类型
     * @param obj 响应
     * @param type 响应类
     * @return 对象
     * @param <T> 泛型
     * @throws IOException
     */
    private <T> T handleResponse(RpcResponse obj, Class<T> type) throws IOException {
        // 将RpcResponse对象中的data字段的参数值序列化为字节数组
        byte[] dataBytes = objectMapper.writeValueAsBytes(obj.getData());
        // 将字节数组反序列化为指定类型的对象
        obj.setData(objectMapper.readValue(dataBytes, obj.getType()));
        return type.cast(obj);
    }

    // 处理RpcRequest对象

    /**
     * 这里都是反序列化之后的处理，再次进行序列反序列的操作
     * @param obj 请求对象
     * @param type 请求的类
     * @return 对象
     * @param <T> 泛型
     * @throws IOException
     */
    private <T> T handleRequest(RpcRequest obj, Class<T> type) throws IOException {
        // 获取RpcRequest对象obj中的参数类型和参数值
        Class<?>[] parameterTypes = obj.getParameterTypes();
        Object[] args = obj.getArgs();

        // 遍历参数类型和参数值
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            // 如果参数类型和参数值的类型不匹配，则将参数值序列化为字节数组，再反序列化为指定类型的对象
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = objectMapper.writeValueAsBytes(args[i]);
                args[i] = objectMapper.readValue(argBytes, clazz);
            }
        }
        // 将obj转换为type类型并返回
        return type.cast(obj);
    }
}