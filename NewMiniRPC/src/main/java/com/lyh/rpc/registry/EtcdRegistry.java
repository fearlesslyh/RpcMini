package com.lyh.rpc.registry;

import cn.hutool.json.JSONUtil;
import io.etcd.jetcd.*;
import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.model.ServiceInfoDefine;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class EtcdRegistry implements Registry {
    private Client client;
    private KV kvClient;
    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/RPC/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getApplyAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTime()))
                .build();
        kvClient = client.getKVClient();
    }

    @Override
    public void registry(ServiceInfoDefine serviceInfoDefine) throws Exception {
// 创建 Lease 和 KV 客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建一个 30 秒的租约
        long leaseId = leaseClient.grant(30).get().getID();

        // 设置要存储的键值对
        String registerKey = ETCD_ROOT_PATH + serviceInfoDefine.getServiceKeys();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceInfoDefine), StandardCharsets.UTF_8);

        // 将键值对与租约关联起来，并设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();
    }

    @Override
    public void Logout(ServiceInfoDefine serviceInfoDefine) {
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceInfoDefine.getServiceKeys(), StandardCharsets.UTF_8));

    }

    @Override
    public List<ServiceInfoDefine> serviceDiscovery(String serviceKeyName) {
        // 前缀搜索，结尾一定要加 '/'
        String searchPrefix = ETCD_ROOT_PATH + serviceKeyName + "/";

        try {
            // 前缀查询
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(
                            ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                            getOption)
                    .get()
                    .getKvs();
            // 解析服务信息
            return keyValues.stream()
                    .map(keyValue -> {
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(value, ServiceInfoDefine.class);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        // 释放资源
        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }
}
