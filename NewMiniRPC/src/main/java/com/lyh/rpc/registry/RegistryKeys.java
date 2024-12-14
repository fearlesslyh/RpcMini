package com.lyh.rpc.registry;

/**
 * @author 梁懿豪
 * @version 1.3
 */

/**
 * 注册的键名，支持两种注册中心
 */
public interface RegistryKeys {
    String ETCD = "etcd";
    String ZOOKEEPER = "zookeeper";
}
