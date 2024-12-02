package com.lyh.rpc;

/**
 * @author 梁懿豪
 * @version 1.0
 */

import com.lyh.rpc.config.RpcConfig;
import com.lyh.rpc.constant.RpcConstant;
import com.lyh.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 维护全局配置的对象，使得不用每次加载配置都创建新的对象，和读取配置，可以直接从这个对象里获取配置信息
 * 使用单例模式
 * 存放了项目全局用到的变量。双检测锁（DCL，即 double-checked locking）的单例模式实现
 */
@Slf4j
public class RPCApplication {

    private static volatile RpcConfig rpcConfig;


    /**
     * 框架初始化，支持传入自定义的配置
     * @param newRpcConfig 自定义的 RPC 配置
     */
    public static void initialize(RpcConfig newRpcConfig){
        rpcConfig=newRpcConfig;
        log.info("RPC启动，config={}",newRpcConfig.toString());
    }



    /**
     * 整体的初始化
     */
    public static void initialize(){
        RpcConfig newRpcConfig;
        try{
            // 从默认配置文件加载配置
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch(Exception e){
            // 加载失败时使用默认配置
            newRpcConfig= new RpcConfig();
        }
        initialize(newRpcConfig);
    }



    /**
     * 获取对象
     *
     * @return 返回全局唯一的 RpcConfig 实例
     */
    public static RpcConfig getInstance() {
        if (rpcConfig == null) {
            synchronized (RpcConfig.class) {
                if (rpcConfig == null) {
                    initialize();
                }
            }
        }
        return rpcConfig;
    }

}
