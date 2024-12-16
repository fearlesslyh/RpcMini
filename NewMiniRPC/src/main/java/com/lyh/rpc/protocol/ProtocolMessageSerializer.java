package com.lyh.rpc.protocol;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Getter
public enum ProtocolMessageSerializer {
    JDK(0, "jdk"),
    JSON(1, "json"),
    KRYO(2, "kryo"),
    HESSIAN(3, "hessian");

    private final int key;

    private final String value;

    ProtocolMessageSerializer(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ProtocolMessageSerializer getFromKey(int Key) {
        for (ProtocolMessageSerializer serializer : ProtocolMessageSerializer.values()) {
            if (serializer.key == Key) {
                return serializer;
            }

        }
        return null;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(item -> item.value)
                .collect(Collectors.toList());
    }

    public static ProtocolMessageSerializer getFromValue(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (ProtocolMessageSerializer serializer : ProtocolMessageSerializer.values()) {
            if (serializer.value.equals(value)) {
                return serializer;
            }
        }
        return null;
    }
}
