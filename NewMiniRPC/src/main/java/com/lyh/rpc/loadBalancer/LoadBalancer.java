package com.lyh.rpc.loadBalancer;

import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;
import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceInfoDefineList 可用服务列表
     *
     */
    ServiceInfoDefine select(Map<String, Object> requestParams, List<ServiceInfoDefine> serviceInfoDefineList);
}
