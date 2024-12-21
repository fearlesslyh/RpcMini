package com.lyh.rpc.fault.retry;

import com.lyh.rpc.SPI.spiLoader;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class RetryMechanismFactory {
    static {
        spiLoader.load(RetryMechanism.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryMechanism DEFAULT_RETRY_STRATEGY = new NoRetryMechanism();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static RetryMechanism getInstance(String key) {
        return spiLoader.getInstance(RetryMechanism.class, key);
    }
}
