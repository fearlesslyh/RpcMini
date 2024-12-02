package com.lyh.rpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author 梁懿豪
 * @version 1.0
 * <p>
 * utils应当通用，不要和业务相关，提高使用的灵活性，支持外层读取配置文件的前缀prefix
 */
//读取配置文件，并且返回配置的对象，可以简化调用

/**
 * utils应当通用，不要和业务相关，提高使用的灵活性，支持外层读取配置文件的前缀prefix
 */

/**
 * 配置工具类
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     * @param tClass 配置对象的类类型
     * @param prefix 配置文件中的前缀
     * @return 回指定类型的配置对象
     * @param <T>配置对象的泛型类型
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，并区分环境
     *
     * @param tClass 配置对象的类类型
     * @param prefix 配置文件中的前缀
     * @param environment 运行环境标识
     * @return 返回指定类型的配置对象
     * @param <T> 配置对象的泛型类型
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}