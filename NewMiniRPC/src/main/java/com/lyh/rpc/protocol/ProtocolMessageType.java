package com.lyh.rpc.protocol;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Getter
//协议消息类型枚举，包括请求、响应、心跳、其他。代码如下：
public enum ProtocolMessageType {
    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageType(int key) {
        this.key = key;
    }

    public static ProtocolMessageType getFromKey(int key) {
        for (ProtocolMessageType type : ProtocolMessageType.values()){
            if (type.key ==key){
                return type;
            }

        }
        return null;
    }


}
