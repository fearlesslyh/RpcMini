package com.lyh.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolInfo<T> {
    private Header header;
    private T body;

@Data
    public static class Header {
        private byte Magic;
        private byte version;
        private byte serializer;
        private byte type;
        private byte status;
        private long id;
        private int bodyLength;
    }

}
