package com.lyh.rpc.config;

import lombok.Data;

/**
 * @author 梁懿豪
 * @version 1.0
 */
@Data
public class RpcConfig {
// 定义RPC框架的名称
    private String name = "miniRPC";

    // 定义RPC框架的版本
    private String version = "1.1";

    // 定义服务器的地址
    private String serverHost = "localhost";

    // 定义服务器的端口号
    private Integer serverPort = 8080;

}
