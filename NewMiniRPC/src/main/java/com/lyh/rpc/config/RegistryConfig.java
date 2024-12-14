package com.lyh.rpc.config;

import lombok.Data;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Data
public class RegistryConfig {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 申请地址，默认为http://localhost:2380
    private String applyAddress="http://localhost:2380";
    // 时间，默认为10000L
    private Long time=10000L;
    // 注册中心，默认为etcd
    private String registry="etcd";
}