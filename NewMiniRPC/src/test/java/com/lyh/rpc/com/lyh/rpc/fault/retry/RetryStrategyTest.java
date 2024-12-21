package com.lyh.rpc.com.lyh.rpc.fault.retry;

import com.lyh.rpc.fault.retry.NoRetryMechanism;
import com.lyh.rpc.fault.retry.RetryMechanism;
import com.lyh.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class RetryStrategyTest {
    RetryMechanism retryStrategy = new NoRetryMechanism();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
