package com.lyh.rpc.loadBalancer;

import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class ConsistentLoadBalancer implements LoadBalancer {
    private final TreeMap<Integer, ServiceInfoDefine> virtualNodes = new TreeMap<>();

    private final static int VIRTUAL_NODE_NUMBER = 100;

    @Override
    public ServiceInfoDefine select(Map<String, Object> requestParams, List<ServiceInfoDefine> serviceInfoDefineList) {
        if (serviceInfoDefineList.isEmpty()) {
            return null;
        }
        for (ServiceInfoDefine serviceInfoDefine : serviceInfoDefineList) {
            for (int i = 0; i < VIRTUAL_NODE_NUMBER; i++) {
                int hash = getHash(serviceInfoDefine.getAddress() + "#" + i);
                virtualNodes.put(hash, serviceInfoDefine);
            }
        }
        int hash=getHash(requestParams);
        // 使用哈希值查找虚拟节点中最小但不小于给定哈希值的元素
        // ceilingEntry 方法返回在此哈希值或之后的最小键的条目，如果不存在这样的条目则返回 null
        Map.Entry<Integer, ServiceInfoDefine> integerServiceInfoDefineEntry = virtualNodes.ceilingEntry(hash);
        if (integerServiceInfoDefineEntry==null){
            integerServiceInfoDefineEntry=virtualNodes.firstEntry();
        }
        return integerServiceInfoDefineEntry.getValue();
    }

    private int getHash(Object key) {
        return key.hashCode();
    }
}
