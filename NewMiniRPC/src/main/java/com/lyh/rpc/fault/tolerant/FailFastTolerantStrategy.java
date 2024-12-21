package com.lyh.rpc.fault.tolerant;

import com.lyh.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
