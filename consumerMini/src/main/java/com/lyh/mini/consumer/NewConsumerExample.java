package com.lyh.mini.consumer;

import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.utils.ConfigUtils;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class NewConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "RPC");
        System.out.println(rpc);
    }
}
