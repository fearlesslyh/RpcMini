package com.lyh.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 梁懿豪
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * RPC响应类，用于封装RPC调用的结果
 * 该类实现了Serializable接口，以支持响应对象的序列化和反序列化
 */
public class RpcResponse implements Serializable {
    // 响应数据，用于存储RPC调用返回的实际数据
    private Object data;

    // 响应数据类型，用于指示返回数据的类型
    private Class<?> type;

    // 响应消息，用于存储RPC调用的附加信息，如成功或错误消息
    private String message;

    // 异常的信息，用于存储RPC调用过程中发生的异常信息
    private Exception exception;
}

