package com.lyh.rpc.registry;

/**
 * @author 梁懿豪
 * @version 1.3
 */

import com.lyh.rpc.SPI.spiLoader;
import com.lyh.rpc.serializer.JDKserializer;
import com.lyh.rpc.serializer.Serializer;

/**
 * 注册中心工厂，用于获取注册中心的对象
 */
public class RegistryFactory {
    static {
        spiLoader.load(Registry.class);
    }
    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_SERIALIZER = new EtcdRegistry();

    /**
     * 获取注册中心的实例
     * @param key
     * @return
     */
    public static Registry getDefaultRegistry (String key) {
        return spiLoader.getInstance(Registry.class, key);
    }
}
