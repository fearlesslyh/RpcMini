package com.lyh.rpc.registry;

import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface Registry {
    /**
     *
     * @param registryConfig  初始化注册中心配置
     */
    void init(RegistryConfig registryConfig);

    /**
     *
     * @param serviceInfoDefine 注册服务信息
     * @throws Exception
     */
    void registry(ServiceInfoDefine serviceInfoDefine)throws Exception;

    /**
     *
     * @param serviceInfoDefine 注销服务
     */
    void Logout(ServiceInfoDefine serviceInfoDefine);

    /**
     *
     * @param serviceKeyName 根据服务名称发现服务信息
     * @return
     */
    List<ServiceInfoDefine> serviceDiscovery(String serviceKeyName);

    /**
     * 销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartBeat();
    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);
}
