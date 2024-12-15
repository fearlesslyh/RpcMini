package com.lyh.rpc.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import io.etcd.jetcd.*;
import com.lyh.rpc.config.RegistryConfig;
import com.lyh.rpc.model.ServiceInfoDefine;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();


    /**
     *
     * @param registryConfig  初始化注册中心配置
     */
    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getApplyAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTime()))
                .build();
        kvClient = client.getKVClient();
        heartBeat();
    }

    /**
     *
     * @param serviceInfoDefine 注册服务信息
     * @throws Exception
     */
    @Override
    public void registry(ServiceInfoDefine serviceInfoDefine) throws Exception {
        // 创建 Lease 和 KV 客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建一个 30 秒的租约
        long leaseId = leaseClient.grant(30)
                .get()
                .getID();

        // 设置要存储的键值对
        // 定义注册的registerKey
        String registerKey = ETCD_ROOT_PATH + serviceInfoDefine.getServiceKeys();
        // 将registerKey转换为ByteSequence类型
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        //value 为服务注册信息的 JSON 序列化
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceInfoDefine), StandardCharsets.UTF_8);

        // 将键值对与租约关联起来，并设置过期时间
        // 创建一个PutOption对象，并设置LeaseId
        PutOption putOption = PutOption.builder()
                .withLeaseId(leaseId)
                .build();
        // 使用kvClient将key和value存入数据库，并使用putOption设置参数
        kvClient.put(key, value, putOption).get();
        //添加节点信息到本地缓存
        localRegisterNodeKeySet.add(registerKey);

    }

    /**
     *
     * @param serviceInfoDefine 注销服务
     */
    @Override
    public void Logout(ServiceInfoDefine serviceInfoDefine) {
        String registerKey = ETCD_ROOT_PATH + serviceInfoDefine.getServiceKeys();
        kvClient.delete(ByteSequence.from(registerKey, StandardCharsets.UTF_8));

        localRegisterNodeKeySet.remove(registerKey);

    }

    /**
     *
     * @param serviceKeyName 根据服务名称发现服务信息
     * @return
     */
    @Override
    public List<ServiceInfoDefine> serviceDiscovery(String serviceKeyName) {
        // 前缀搜索，结尾一定要加 '/'
        String searchPrefix = ETCD_ROOT_PATH + serviceKeyName + "/";

        try {
            // 前缀查询
            // 创建一个GetOption对象，设置isPrefix为true
  GetOption getOption = GetOption.builder()
                    .isPrefix(true)
                    .build();
            // 使用kvClient的get方法，传入ByteSequence对象和GetOption对象，获取keyValues列表
            List<KeyValue> keyValues = kvClient.get(
                            ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                            getOption)
                    .get()
                    .getKvs();
            // 解析服务信息
            // 将keyValues流中的每个keyValue对象转换为ServiceInfoDefine对象，并收集到一个List中
       return keyValues.stream()
                    // 将keyValue键值对象中的value属性转换为字符串
                    .map(keyValue -> {
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        // 将字符串转换为ServiceInfoDefine对象
                        return JSONUtil.toBean(value, ServiceInfoDefine.class);
                    })
                    // 将转换后的ServiceInfoDefine对象收集到一个List中
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    /**
     * 注册中心销毁，当项目关闭，销毁，释放资源
     */
    @Override
    public void destroy() {
        System.out.println("当前节点下线");

        for(String key:localRegisterNodeKeySet){
            try {
                kvClient.delete(ByteSequence.from(key,StandardCharsets.UTF_8)).get();
            }catch (Exception e){
                throw new RuntimeException(key+"节点下线失败");
            }
        }

        // 释放资源
        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void heartBeat() {
        CronUtil.schedule("*/10 * * * * *", (Task) () -> {
            for (String key: localRegisterNodeKeySet){
                try {
                    List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                            .get()
                            .getKvs();
                    if(CollUtil.isEmpty(keyValues)){
                        continue;
                    }
                    //节点未过期，重新注册，相当于续期
                    KeyValue keyValue = keyValues.get(0);
                    String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                    ServiceInfoDefine serviceInfoDefine = JSONUtil.toBean(value, ServiceInfoDefine.class);
                    registry(serviceInfoDefine);
                }catch (Exception e){
                    throw new RuntimeException(key+ " 续签失败 "+ e);
                }
            }
        });
        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}
