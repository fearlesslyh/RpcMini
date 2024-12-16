package com.lyh.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.constant.RpcConstant;
import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.protocol.*;
import com.lyh.rpc.registry.Registry;
import com.lyh.rpc.registry.RegistryFactory;
import com.lyh.rpc.serializer.JDKserializer;
import com.lyh.rpc.serializer.Serializer;
import com.lyh.rpc.serializer.SerializerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            RpcConfig rpcConfig = RPCApplication.getInstance();
            Registry registry = RegistryFactory.getDefaultRegistry(rpcConfig.getRegistryConfig().getRegistry());
            ServiceInfoDefine ServiceInfoDefine = new ServiceInfoDefine();
            ServiceInfoDefine.setServiceName(serviceName);
            ServiceInfoDefine.setServiceVersion(RpcConstant.DEFAULT_VERSION);
            List<ServiceInfoDefine> ServiceInfoDefineList = registry.serviceDiscovery(ServiceInfoDefine.getKey());
            if (CollUtil.isEmpty(ServiceInfoDefineList)) {
                throw new RuntimeException("暂无服务地址");
            }
            ServiceInfoDefine selectedServiceInfoDefine = ServiceInfoDefineList.get(0);
            // 发送 TCP 请求
            Vertx vertx = Vertx.vertx();
            NetClient netClient = vertx.createNetClient();
            CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
            netClient.connect(selectedServiceInfoDefine.getServicePort(), selectedServiceInfoDefine.getServiceHost(),
                    result -> {
                        if (result.succeeded()) {
                            System.out.println("Connected to TCP server");
                            io.vertx.core.net.NetSocket socket = result.result();
                            // 发送数据
                            // 构造消息
                            ProtocolInfo<RpcRequest> ProtocolInfo = new ProtocolInfo<>();
                            ProtocolInfo.Header header = new ProtocolInfo.Header();
                            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                            header.setSerializer((byte) ProtocolMessageSerializer.getFromValue(RPCApplication.getInstance().getSerializer()).getKey());
                            header.setType((byte) ProtocolMessageType.REQUEST.getKey());
                            header.setId(IdUtil.getSnowflakeNextId());
                            ProtocolInfo.setHeader(header);
                            ProtocolInfo.setBody(rpcRequest);
                            // 编码请求
                            try {
                                Buffer encodeBuffer = ProtocolMessageEncoder.encode(ProtocolInfo);
                                socket.write(encodeBuffer);
                            } catch (IOException e) {
                                throw new RuntimeException("协议消息编码错误");
                            }

                            // 接收响应
                            socket.handler(buffer -> {
                                try {
                                    ProtocolInfo<RpcResponse> rpcResponseProtocolInfo = (ProtocolInfo<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolInfo.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException("协议消息解码错误");
                                }
                            });
                        } else {
                            System.err.println("Failed to connect to TCP server");
                        }
                    });

            RpcResponse rpcResponse = responseFuture.get();
            // 记得关闭连接
            netClient.close();
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}