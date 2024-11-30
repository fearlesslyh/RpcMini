package com.lyh.mini.provider;

import com.lyh.mini.common.model.User;
import com.lyh.mini.common.service.UserService;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * UserService 实现类
 */
public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.println("用户名称 "+user.getName());
        return user;
    }
}
