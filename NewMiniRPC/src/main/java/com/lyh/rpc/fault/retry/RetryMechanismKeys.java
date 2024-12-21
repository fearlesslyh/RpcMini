package com.lyh.rpc.fault.retry;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface RetryMechanismKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
