package com.lyh.rpc.registry;

import com.lyh.rpc.model.ServiceInfoDefine;

import java.util.List;

/**
 * @author 梁懿豪
 * @version 1.3
 */
public class RegistryCache {
    List<ServiceInfoDefine>serviceCache;

    void writeCache(List<ServiceInfoDefine>newServiceCache){
        this.serviceCache=newServiceCache;
    }
    List<ServiceInfoDefine> read(){
        return this.serviceCache;
    }

    void delete(){
        this.serviceCache=null;
    }
}
