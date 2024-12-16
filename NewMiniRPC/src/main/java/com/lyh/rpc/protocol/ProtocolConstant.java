package com.lyh.rpc.protocol;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public interface ProtocolConstant {
    int MESSAGE_HEADER_LENGTH = 17;
    byte PROTOCOL_MAGIC=0x1;
    byte PROTOCOL_VERSION=0x1;
}
