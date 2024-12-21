package com.lyh.mini.consumer;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.mini.common.model.User;
import com.lyh.mini.common.service.UserService;
import com.lyh.rpc.bootstrap.ConsumerBootstrap;
import com.lyh.rpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者启动类
 */
public class consumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("梁懿豪");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
