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
    // 根据传入的Class对象和前缀，加载配置文件
  public static <T> T loadConfig(Class<T> tClass, String prefix) {
        // 调用loadConfig方法，传入Class对象、前缀和空字符串
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
        // 创建一个StringBuilder对象，用于拼接配置文件名
        StringBuilder configFileBuilder = new StringBuilder("application");
        // 如果环境变量不为空，则将环境变量拼接到配置文件名中，这种就是对字符串的拼接，最后形成类似这种application-prod/dev.xxx
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        // 拼接配置文件后缀名,这样就变成application(-dev/prod).properties类似的
        configFileBuilder.append(".properties");
        // 创建Props对象，用于读取配置文件
        Props props = new Props(configFileBuilder.toString());
        // 将配置文件中的属性转换为指定类型的对象
        return props.toBean(tClass, prefix);
    }
}