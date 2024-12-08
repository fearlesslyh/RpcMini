package com.lyh.rpc.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class SerializerFactory {
    // 定义一个静态常量，用于存储常用的序列化器
    private static final Map<String, Serializer> COMMON_SERIALIZER_MAP = new HashMap<>();

    // 静态代码块，用于初始化常用的序列化器
    static {
        // 将JDK序列化器放入COMMON_SERIALIZER_MAP中
        COMMON_SERIALIZER_MAP.put(SerializerCommons.JDK, new JDKserializer());
        // 将Hessian序列化器放入COMMON_SERIALIZER_MAP中
        COMMON_SERIALIZER_MAP.put(SerializerCommons.HESSIAN, new HessianSerializer());
        // 将JSON序列化器放入COMMON_SERIALIZER_MAP中
        COMMON_SERIALIZER_MAP.put(SerializerCommons.JSON, new JsonSerializer());
        // 将Kryo序列化器放入COMMON_SERIALIZER_MAP中
        COMMON_SERIALIZER_MAP.put(SerializerCommons.KRYO, new KryoSerializer());

    }

    // 定义一个静态常量，用于存储默认的序列化器
    private static final Serializer DEFAULT_SERIALIZER = COMMON_SERIALIZER_MAP.get("jdk");
    // 根据key获取序列化器，如果key不存在，则返回默认的序列化器
    public static Serializer getSerializer(String key){
        return COMMON_SERIALIZER_MAP.getOrDefault(key, DEFAULT_SERIALIZER);
    }
}
