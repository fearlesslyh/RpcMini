package com.lyh.rpc.loadBalancer;

import com.lyh.rpc.SPI.spiLoader;
import io.grpc.util.RoundRobinLoadBalancer;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class LoadBalancerFactory {
    static {
        spiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundLoadBalancer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return spiLoader.getInstance(LoadBalancer.class, key);
    }
}
