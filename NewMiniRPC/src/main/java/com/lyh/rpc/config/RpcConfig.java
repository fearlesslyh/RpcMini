package com.lyh.rpc.config;

import com.lyh.rpc.serializer.SerializerCommons;
import com.lyh.rpc.serializer.SerializerFactory;
import lombok.Data;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * 保存配置信息，RPC框架配置
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

    //模拟调用
    private boolean mock = false;

    //序列化器
    private String Serializer = SerializerCommons.JDK;

    // 定义一个RegistryConfig类型的变量registryConfig，注册中心的配置
    private RegistryConfig registryConfig = new RegistryConfig();

}
