package com.lyh.rpc.model;

/**
 * @author 梁懿豪
 * @version 1.3
 */
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


}
