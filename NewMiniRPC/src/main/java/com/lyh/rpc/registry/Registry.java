package com.lyh.rpc.registry;

import com.lyh.rpc.config.RegistryConfig;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface Registry {
    void init();
    void registry(RegistryConfig registryConfig);
    void Logout();
    void destroy();

}
