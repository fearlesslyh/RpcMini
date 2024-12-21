package com.lyh.rpc.server.TCP;

import com.lyh.rpc.model.RpcRequest;
import com.lyh.rpc.model.RpcResponse;
import com.lyh.rpc.protocol.ProtocolInfo;
import com.lyh.rpc.protocol.ProtocolMessageDecoder;
import com.lyh.rpc.protocol.ProtocolMessageEncoder;
import com.lyh.rpc.protocol.ProtocolMessageType;
import com.lyh.rpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class TcpServerHandler implements Handler<NetSocket> {

    /**
     * 处理请求
     *
     * @param socket the event to handle
     */
    @Override
    public void handle(NetSocket socket) {
        TcpBufferWrapper bufferHandlerWrapper = new TcpBufferWrapper(buffer -> {
            // 处理请求代码
        });
        socket.handler(bufferHandlerWrapper);
    }
}
