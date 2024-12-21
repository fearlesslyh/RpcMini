package com.lyh.rpc.fault.tolerant;

import com.lyh.rpc.model.RpcResponse;

import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
