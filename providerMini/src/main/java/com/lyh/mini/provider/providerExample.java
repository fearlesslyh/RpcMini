package com.lyh.mini.provider;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.mini.common.service.UserService;
import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.registry.LocalRegistry;
import com.lyh.rpc.registry.Registry;
import com.lyh.rpc.registry.RegistryFactory;
import com.lyh.rpc.server.Httpserver;
import com.lyh.rpc.server.TCP.VertxTcpServer;
import com.lyh.rpc.server.VertxHttpServer;

/**
 * 服务提供者端
 * 启动类
 */
public class providerExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RPCApplication.initialize();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RPCApplication.getInstance();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getDefaultRegistry(registryConfig.getRegistry());
        ServiceInfoDefine serviceMetaInfo = new ServiceInfoDefine();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.registry(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.start(8080);
//        // 启动 web 服务
//        Httpserver httpServer = new VertxHttpServer();
//        httpServer.start(RPCApplication.getInstance().getServerPort());
    }
}
