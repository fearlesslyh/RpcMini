package com.lyh.rpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Data
public class ServiceInfoDefine {
    private String ServiceName;
    private String serviceHost;
    private String serviceVersion="1.0";
    private Integer servicePort;
    private String serviceGroup="default";

    public String getKey(){
        return String.format("%s:%s:%s",ServiceName,serviceVersion,serviceGroup);
    }

    public  String getServiceKeys(){
        return  String.format("%s/%s:%s", getKey(), serviceHost, servicePort);
    }

    public String getAddress(){
        if (!StrUtil.contains(serviceHost,"http")){
            return String.format("http://%s:%s",serviceHost,servicePort);
        }
        return String.format("%s:%s",serviceHost,servicePort);
    }
}
