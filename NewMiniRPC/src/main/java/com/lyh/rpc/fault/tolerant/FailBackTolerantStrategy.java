package com.lyh.rpc.fault.tolerant;

import com.lyh.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class FailBackTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取降级的服务并调用
        return null;
    }
}
