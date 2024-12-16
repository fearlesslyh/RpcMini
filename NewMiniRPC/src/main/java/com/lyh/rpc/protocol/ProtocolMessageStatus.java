package com.lyh.rpc.protocol;

import lombok.Getter;

/**
 * @author 梁懿豪
 * @version 1.3
 */

//协议状态枚举，暂时只定义成功、请求失败、响应失败三种枚举值
@Getter
public enum ProtocolMessageStatus {
    OK("ok",20),
    BAD_REQUEST("badRequest",40),
    BAD_RESPONSE("badResponse",50);
    private final String text;
    private final int value;

    ProtocolMessageStatus(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public static ProtocolMessageStatus getFromValue(int value) {
        for (ProtocolMessageStatus status : ProtocolMessageStatus.values()){
            if (status.value ==value){
                return status;
            }

        }
        return null;
    }
}
