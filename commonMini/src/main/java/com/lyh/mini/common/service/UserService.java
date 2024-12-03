package com.lyh.mini.common.service;

import com.lyh.mini.common.model.User;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * 获取用户
 */
public interface UserService {
    /**
     * 获取用户
     * @param user 用户
     * @return 无
     */
    User getUser(User user);
    default short getNumber(){
        return 1;
    };
}
