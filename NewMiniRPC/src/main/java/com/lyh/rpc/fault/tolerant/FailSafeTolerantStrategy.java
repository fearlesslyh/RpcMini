package com.lyh.rpc.fault.tolerant;

import com.lyh.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
