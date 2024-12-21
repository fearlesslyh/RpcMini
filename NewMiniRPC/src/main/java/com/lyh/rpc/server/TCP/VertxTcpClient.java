package com.lyh.rpc.server.TCP;

import cn.hutool.core.util.IdUtil;
import com.lyh.rpc.RPCApplication;
import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;
import com.lyh.rpc.model.ServiceInfoDefine;
import com.lyh.rpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class VertxTcpClient {
    /**
     * 发送请求
     *
     * @param rpcRequest
     * @param
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceInfoDefine serviceInfoDefine) throws InterruptedException, ExecutionException {
        // 发送 TCP 请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceInfoDefine.getServicePort(), serviceInfoDefine.getServiceHost(),
                result -> {
                    if (!result.succeeded()) {
                        System.err.println("Failed to connect to TCP server");
                        return;
                    }
                    NetSocket socket = result.result();
                    // 发送数据
                    // 构造消息
                    ProtocolInfo<RpcRequest> protocolMessage = new ProtocolInfo<>();
                    ProtocolInfo.Header header = new ProtocolInfo.Header();
                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                    header.setSerializer((byte) ProtocolMessageSerializer.getFromValue(RPCApplication.getInstance().getSerializer()).getKey());
                    header.setType((byte) ProtocolMessageType.REQUEST.getKey());
                    // 生成全局请求 ID
                    header.setId(IdUtil.getSnowflakeNextId());
                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);

                    // 编码请求
                    try {
                        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                        socket.write(encodeBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息编码错误");
                    }

                    // 接收响应
                    TcpBufferWrapper bufferHandlerWrapper = new TcpBufferWrapper(
                            buffer -> {
                                try {
                                    ProtocolInfo<RpcResponse> rpcResponseProtocolMessage =
                                            (ProtocolInfo<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException("协议消息解码错误");
                                }
                            }
                    );
                    socket.handler(bufferHandlerWrapper);

                });

        RpcResponse rpcResponse = responseFuture.get();
        // 记得关闭连接
        netClient.close();
        return rpcResponse;
    }
}
