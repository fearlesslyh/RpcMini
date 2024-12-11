package com.lyh.rpc.config;

import lombok.Data;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Data
public class RegistryConfig {
    private String username;
    private String password;
    private String applyAddress="http://localhost:2310";
    private Long time=10000L;
    private String registry="etcd";
}
