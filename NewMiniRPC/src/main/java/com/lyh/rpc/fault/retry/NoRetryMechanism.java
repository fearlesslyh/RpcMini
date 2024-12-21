package com.lyh.rpc.fault.retry;

import com.lyh.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Slf4j
public class NoRetryMechanism implements RetryMechanism{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
