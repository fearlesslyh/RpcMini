package com.lyh.rpc.fault.tolerant;

import com.lyh.rpc.SPI.spiLoader;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class TolerantStrategyFactory {
    static {
        spiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return spiLoader.getInstance(TolerantStrategy.class, key);
    }
}
