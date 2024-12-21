package com.lyh.springbootconsumer1;

import com.lyh.mini.common.model.User;
import com.lyh.mini.common.service.UserService;
import com.lyh.springbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Service
public class ServiceImpl {
    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("梁懿豪");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
