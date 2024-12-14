package com.lyh.rpc.model;

import com.lyh.rpc.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * 它实现了 Serializable 接口，以支持对象的序列化和反序列化
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    // 序列化版本标识符，用于反序列化时版本控制
    private static final long serialVersionUID = 1L;

    /**
     * 服务名称，用于标识调用的服务
     * 它通常对应于服务提供者注册的服务名称
     */
    private String serviceName;

    /**
     * 方法名称，用于标识在服务中调用的具体方法
     * 通过服务名称和方法名称，可以定位到具体的服务方法
     */
    private String methodName;

    /**
     * 参数类型，表示调用方法所需的参数类型
     * 这用于在反射机制中匹配和调用正确的服务方法
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数值列表，包含调用方法时传递的实际参数值
     * 它与 parameterTypes 数组配合使用，确保参数类型和值的正确性
     */
    private Object[] args;

    // 定义一个私有字符串变量serviceVersion，初始值为RpcConstant.DEFAULT_VERSION
    private String serviceVersion= RpcConstant.DEFAULT_VERSION;
}

