package com.lyh.rpc.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class LocalRegistry {
//    Class类型是反射机制的核心部分,表明类的名称,concurrent hashmap线程安全,和hashmap相比,用于多线程,存储键值对
//    final写了,map不能再赋值,键值对可以改变,static表示静态,静态变量属于类变量,所有对象都共享同一份变量,不会再被创建
//    注册信息存储
    private static final ConcurrentHashMap<String, Class<?>> map =new ConcurrentHashMap<>();
    /*
      注册服务
     */
    public static void register(String serviceName, Class<?> implClass){
        map.put(serviceName,implClass);
    }
    /**
     * 获取服务
     */
//      共享性：静态成员属于类本身，而不是类的实例。所有实例共享同一个静态成员。
//      内存分配：静态成员在类加载时被分配内存，且只分配一次。
//      访问方式：可以通过类名直接访问静态成员，无需创建类的实例。例如，ClassName.staticMethod() 或 ClassName.staticVariable
    public static Class<?> getServiceName(String serviceName){
        return map.get(serviceName);
    }
    /**
     * 删除服务
     */
    public static void deleteService(String serviceName){
        map.remove(serviceName);
    }

}
