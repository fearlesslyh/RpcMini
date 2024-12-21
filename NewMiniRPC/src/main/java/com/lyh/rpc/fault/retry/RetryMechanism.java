package com.lyh.rpc.fault.retry;

import com.lyh.rpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface RetryMechanism {
    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
