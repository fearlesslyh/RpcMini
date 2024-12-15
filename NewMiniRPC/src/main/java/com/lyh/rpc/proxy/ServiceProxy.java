package com.lyh.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.constant.RpcConstant;
import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.registry.Registry;
import com.lyh.rpc.registry.RegistryFactory;
import com.lyh.rpc.serializer.JDKserializer;
import com.lyh.rpc.serializer.Serializer;
import com.lyh.rpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getSerializer(RPCApplication.getInstance().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            // 获取RPC配置
      RpcConfig rpcConfig = RPCApplication.getInstance();
      // 获取默认的注册中心
      Registry registry = RegistryFactory.getDefaultRegistry(rpcConfig.getRegistryConfig().getRegistry());
      // 创建服务信息定义
      ServiceInfoDefine serviceMetaInfo = new ServiceInfoDefine();
      // 设置服务名称
      serviceMetaInfo.setServiceName(serviceName);
      // 设置服务版本
      serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_VERSION);
      // 根据服务信息定义获取服务列表
      List<ServiceInfoDefine> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getKey());
      // 如果服务列表为空，则抛出异常
      if (CollUtil.isEmpty(serviceMetaInfoList)) {
          throw new RuntimeException("暂无服务地址");
      }
      // 获取第一个服务信息定义
      ServiceInfoDefine selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}