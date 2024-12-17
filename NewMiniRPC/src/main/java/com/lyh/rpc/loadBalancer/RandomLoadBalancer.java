package com.lyh.rpc.loadBalancer;

import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class RandomLoadBalancer implements LoadBalancer{
    private final Random random = new Random();
    @Override
    public ServiceInfoDefine select(Map<String, Object> requestParams, List<ServiceInfoDefine> serviceInfoDefineList) {
        int size = serviceInfoDefineList.size();
        if (size == 0) {
            return null;
        }
        // 只有 1 个服务，不用随机
        if (size == 1) {
            return serviceInfoDefineList.get(0);
        }
        return serviceInfoDefineList.get(random.nextInt(size));
    }
}
