package com.lyh.mini.consumer;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.mini.common.model.User;
import com.lyh.mini.common.service.UserService;
import com.lyh.rpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者启动类
 */
public class consumerExample {
    public static void main(String[] args) {
        //静态代理
//        UserService userService =  new UserServiceProxy();
//        动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("无畏");
        User newUser = userService.getUser(user);
        if (newUser !=null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("用户不存在");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
