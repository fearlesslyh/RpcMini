package com.lyh.rpc.loadBalancer;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";
}
