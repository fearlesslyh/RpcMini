package com.lyh.rpc.server;

import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;
import com.lyh.rpc.registry.LocalRegistry;
import com.lyh.rpc.serializer.JDKserializer;
import com.lyh.rpc.serializer.Serializer;
import com.lyh.rpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * HTTP请求处理
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    public void handle(HttpServerRequest request) {
// 指定序列化器
        final Serializer serializer = SerializerFactory.getSerializer(RPCApplication.getInstance().getSerializer());
        System.out.println("收到请求 " + request.method() + " " + request.uri());
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("请求为空");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try{
                //获取要调用的服务实现类，并通过反射机制调用
                Class<?> serviceNameIml = LocalRegistry.getServiceName(rpcRequest.getServiceName());
                Method method = serviceNameIml.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(serviceNameIml.newInstance(), rpcRequest.getArgs());

                rpcResponse.setData(result);
                rpcResponse.setMessage("ok了");
                rpcResponse.setType(method.getReturnType());
            }catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setException(e);
                rpcResponse.setMessage(e.getMessage());
            }

            doResponse(request, rpcResponse, serializer);
        });
    }

    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");
        try {
            byte[] serializing = serializer.serialize(rpcResponse);
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
