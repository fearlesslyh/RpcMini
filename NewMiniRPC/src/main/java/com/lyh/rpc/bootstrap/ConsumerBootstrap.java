package com.lyh.rpc.bootstrap;

import com.lyh.rpc.RPCApplication;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class ConsumerBootstrap {
    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RPCApplication.initialize();
    }

}
