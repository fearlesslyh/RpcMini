package com.lyh.mini.provider;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.mini.common.service.UserService;
import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.bootstrap.ProviderBootstrap;
import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.model.ServiceRegisterInfo;
import com.lyh.rpc.registry.LocalRegistry;
import com.lyh.rpc.registry.Registry;
import com.lyh.rpc.registry.RegistryFactory;
import com.lyh.rpc.server.Httpserver;
import com.lyh.rpc.server.TCP.VertxTcpServer;
import com.lyh.rpc.server.VertxHttpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者端
 * 启动类
 */
public class providerExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
