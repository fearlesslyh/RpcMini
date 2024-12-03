package com.lyh.rpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static io.vertx.core.http.impl.HttpClientConnection.log;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Slf4j
public class MockProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据返回的方法的类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        //这里的{}是占位符，用来替代后面的参数
        log.info("mock调用 {}", method.getName());
        return getDefaultObject(methodReturnType);
    }

    private Object getDefaultObject(Class<?> methodReturnType) {
        if (methodReturnType.isPrimitive()) {
            if (methodReturnType == boolean.class) {
                return false;
            } else if (methodReturnType == short.class) {
                return (short) 0;
            } else if (methodReturnType == int.class) {
                return 0;
            } else if (methodReturnType == long.class) {
                return 0L;
            } else if (methodReturnType == float.class) {
                return 0.0f;
            } else if (methodReturnType == double.class) {
                return 0.0d;
            }
        }
        //对象类型
        return null;
    }
}
