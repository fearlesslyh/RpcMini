package com.lyh.rpc.loadBalancer;

import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class RoundLoadBalancer implements LoadBalancer {
    /**
     * 当前轮询的下标
     */
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public ServiceInfoDefine select(Map<String, Object> requestParams, List<ServiceInfoDefine> serviceInfoDefineList) {
        if (serviceInfoDefineList.isEmpty()) {
            return null;
        }
        int size = serviceInfoDefineList.size();
        // 只有一个服务，无需轮询
        if (size == 1) {
            return serviceInfoDefineList.get(0);
        }
        // 取模算法轮询
        int index = currentIndex.getAndIncrement() % size;
        return serviceInfoDefineList.get(index);
    }
}
