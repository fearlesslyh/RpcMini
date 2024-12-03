package com.lyh.rpc.proxy;

import com.lyh.rpc.RPCApplication;

import java.lang.reflect.Proxy;

/**
 * @author 梁懿豪
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ServiceProxyFactory {
    /**
     *
     * 根据服务类获取代理对象
     * @param serviceClass 服务类
     * @return 新建的代理对象
     * @param <T> 泛型
     */
    // 泛型方法，返回类型为T
    public static  <T> T getProxy(Class<T> serviceClass) {
        // 判断RPCApplication实例是否为mock模式
        if (RPCApplication.getInstance().isMock()) {
            // 返回mock代理对象
            return getMockProxy(serviceClass);
        }

        // 使用Proxy类创建代理对象
        return (T) Proxy.newProxyInstance(
                // 传入serviceClass的类加载器
                serviceClass.getClassLoader(),
                // 传入serviceClass的Class数组
                new Class[]{serviceClass},
                // 传入ServiceProxy对象
                new ServiceProxy());
    }

    /**
     * 
     *  根据服务类获取mock的代理对象
     * @param serviceClass 服务类
     * @return 新建的代理对象
     * @param <T> 泛型
     */
    // 根据传入的Class对象，获取该Class对象的Mock代理对象
    public static <T>T getMockProxy(Class<T> serviceClass){
        // 使用Proxy.newProxyInstance方法创建代理对象
        return (T)Proxy.newProxyInstance(
                // 传入Class对象的类加载器
                serviceClass.getClassLoader(),
                // 传入Class对象
                new Class[]{serviceClass},
                // 传入MockProxy对象
                new MockProxy()
        );
    }
}
