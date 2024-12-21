package com.lyh.springbootprovider1;

import com.lyh.springbootstarter.annotation.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@SpringBootApplication
@EnableRpc
public class SpringbootProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootProviderApplication.class, args);
    }
}
