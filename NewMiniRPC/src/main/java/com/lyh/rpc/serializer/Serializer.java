package com.lyh.rpc.serializer;

import java.io.IOException;

/**
 * @author 梁懿豪
 * @version 1.0
 */
public interface Serializer {
    /**
     *序列化
     * @param value
     * @return
     * @param <T>
     * @throws IOException
     */
//   <T>是指泛型, T是类型参数,是指任何类型,double,string这些是具体类型,<T>是特意加在方法前面的,是为了区分这个方法可以接受任何类型参数
//    byte[] serialize(T value) throws IOException;这样会报错,无法解析T是什么,所以<T>要加在方法前面,才不会报错
     <T> byte[] serialize(T value) throws IOException;

    /**
     *反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;

}
