package com.lyh.mini.provider;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.mini.common.service.UserService;
import com.lyh.rpc.registry.LocalRegistry;
import com.lyh.rpc.server.VertxHttpServer;

/**
 * 服务提供者端
 * 启动类
 */
public class providerExample {
    public static void main(String[] args) {
//      注册服务(可以直接调用Local Registry的静态方法,而无需创造实例)
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
//        启动web服务
        new VertxHttpServer().start(8080);
        System.out.println("启动成功");
    }
}
