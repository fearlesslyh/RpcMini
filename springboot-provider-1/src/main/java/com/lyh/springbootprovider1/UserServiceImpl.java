package com.lyh.springbootprovider1;

import com.lyh.mini.common.model.User;
import com.lyh.mini.common.service.UserService;
import com.lyh.springbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}