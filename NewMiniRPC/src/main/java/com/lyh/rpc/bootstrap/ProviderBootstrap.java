package com.lyh.rpc.bootstrap;

import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.model.ServiceRegisterInfo;
import com.lyh.rpc.registry.LocalRegistry;
import com.lyh.rpc.registry.Registry;
import com.lyh.rpc.registry.RegistryFactory;
import com.lyh.rpc.server.TCP.VertxTcpServer;

import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RPCApplication.initialize();
        // 全局配置
        final RpcConfig rpcConfig = RPCApplication.getInstance();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getDefaultRegistry(registryConfig.getRegistry());
            ServiceInfoDefine serviceInfoDefine = new ServiceInfoDefine();
            serviceInfoDefine.setServiceName(serviceName);
            serviceInfoDefine.setServiceHost(rpcConfig.getServerHost());
            serviceInfoDefine.setServicePort(rpcConfig.getServerPort());
            try {
                registry.registry(serviceInfoDefine);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.start(rpcConfig.getServerPort());
    }
}
