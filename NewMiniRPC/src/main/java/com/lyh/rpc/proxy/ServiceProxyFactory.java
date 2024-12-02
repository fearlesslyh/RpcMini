package com.lyh.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class ServiceProxyFactory {
    public static  <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
