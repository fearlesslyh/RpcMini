package com.lyh.rpc.protocol;

import cn.hutool.core.util.IdUtil;
import com.lyh.rpc.constant.RpcConstant;
import com.lyh.rpc.model.RpcRequest;
import io.vertx.core.buffer.Buffer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class MessageTest {
    @Test
    public void testEncode() throws IOException {
        // 构造消息
        ProtocolInfo<RpcRequest> protocolMessage = new ProtocolInfo<>();
        ProtocolInfo.Header header = new ProtocolInfo.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializer.JDK.getKey());
        header.setType((byte) ProtocolMessageType.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatus.OK.getValue());
        header.setId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(0);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("myService");
        rpcRequest.setMethodName("myMethod");
        rpcRequest.setServiceVersion(RpcConstant.DEFAULT_VERSION);
        rpcRequest.setParameterTypes(new Class[]{String.class});
        rpcRequest.setArgs(new Object[]{"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);

        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolInfo<?> message = ProtocolMessageDecoder.decode(encodeBuffer);
        Assert.assertNotNull(message);
    }
}
