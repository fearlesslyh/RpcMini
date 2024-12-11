package com.lyh.rpc.registry;

import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface Registry {
    void init(RegistryConfig registryConfig);
    void registry(ServiceInfoDefine serviceInfoDefine)throws Exception;
    void Logout(ServiceInfoDefine serviceInfoDefine);
    List<ServiceInfoDefine> serviceDiscovery(String serviceKeyName);
    void destroy();

}
