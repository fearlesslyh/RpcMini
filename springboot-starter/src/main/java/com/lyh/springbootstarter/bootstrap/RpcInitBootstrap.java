package com.lyh.springbootstarter.bootstrap;

import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.server.TCP.VertxTcpServer;
import com.lyh.springbootstarter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    /**
     * Spring 初始化时执行，初始化 RPC 框架
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableRpc 注解的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName())
                .get("needServer");

        // RPC 框架初始化（配置和注册中心）
        RPCApplication.initialize();

        // 全局配置
        final RpcConfig rpcConfig = RPCApplication.getInstance();

        // 启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.start(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }

    }
}
