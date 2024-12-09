package com.lyh.rpc.SPI;

import cn.hutool.core.io.resource.ResourceUtil;
import com.lyh.rpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@Slf4j
public class spiLoader {
    // 存储加载的SPI
    private static Map<String, Map<String, Class<?>>> LoaderMap = new ConcurrentHashMap<>();
    // 存储实例化的对象
    private static Map<String, Object> objCache = new ConcurrentHashMap<>();
    // 系统SPI路径
    private static final String RPC_SPI_SYSTEM_DIR = "META-INF/rpc/system/";
    // 用户自定义SPI路径
    private static final String RPC_SPI_CUSTOM_DIR = "META-INF/rpc/custom/";
    // 扫描路径
    private static final String[] SCANNER = new String[]{RPC_SPI_SYSTEM_DIR, RPC_SPI_CUSTOM_DIR};
    // 需要加载的类列表
    private static final List<Class<?>> Load_CLASS_LIST = Arrays.asList(Serializer.class);

    // 加载所有的SPI
    private static void loadEverything() {
        log.info("加载所有的SPI");
        for (Class<?> aClass : Load_CLASS_LIST) {
            load(aClass);
        }
    }

    // 加载指定类型的SPI
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的 SPI 优先级高于系统 SPI
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCANNER) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            // 读取每个资源文件
            // 遍历resources中的每一个URL
      for (URL resource : resources) {
                try {
                    // 创建InputStreamReader对象，用于读取URL中的数据
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    // 创建BufferedReader对象，用于按行读取数据
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    // 定义一个字符串变量，用于存储每一行的数据
                    String line;
                    // 循环读取每一行的数据
                    while ((line = bufferedReader.readLine()) != null) {
                        // 将每一行的数据按照"="进行分割
                        String[] strArray = line.split("=");
                        // 如果分割后的数组长度大于1，说明该行数据是有效的
                        if (strArray.length > 1) {
                            // 获取分割后的第一个元素作为key
                            String key = strArray[0];
                            // 获取分割后的第二个元素作为className
                            String className = strArray[1];
                            // 将key和className放入keyClassMap中
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                } catch (Exception e) {
                    // 如果出现异常，则打印错误日志
                    log.error("SPI资源加载错误", e);
                }
            }
        }
        // 将loadClass的名称和keyClassMap放入LoaderMap中
        LoaderMap.put(loadClass.getName(), keyClassMap);
        // 返回keyClassMap
        return keyClassMap;
    }

    // 获取指定类型的实例
    public static <T> T getInstance(Class<?> tClass, String key) {
        // 获取tClass的类名
   String tClassName = tClass.getName();
   // 获取tClassName对应的keyClassMap
   Map<String, Class<?>> keyClassMap = LoaderMap.get(tClassName);
   // 如果keyClassMap为空，则抛出异常
   if (keyClassMap == null) {
       throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
   }
   // 如果keyClassMap中不包含key，则抛出异常
   if (!keyClassMap.containsKey(key)) {
       throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key= %s 的类型", tClassName, key));
   }
        // 获取到要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);
        // 从实例缓存中加载指定类型的实例
        String implClassName = implClass.getName();
        // 如果objCache中不包含implClassName
  if (!objCache.containsKey(implClassName)) {
            try {
                // 尝试将implClass实例化并存入objCache中
                objCache.put(implClassName, implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                // 如果实例化失败，抛出RuntimeException
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            }
        }
        // 返回objCache中implClassName对应的对象
        return (T) objCache.get(implClassName);
    }

}
//package com.yupi.yurpc.spi;
//
//import cn.hutool.core.io.resource.ResourceUtil;
//import com.yupi.yurpc.serializer.Serializer;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * SPI 加载器（支持键值对映射）
// */
//@Slf4j
//public class SpiLoader {
//
//    /**
//     * 存储已加载的类：接口名 =>（key => 实现类）
//     */
//    private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();
//
//    /**
//     * 对象实例缓存（避免重复 new），类路径 => 对象实例，单例模式
//     */
//    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();
//
//    /**
//     * 系统 SPI 目录
//     */
//    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";
//
//    /**
//     * 用户自定义 SPI 目录
//     */
//    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";
//
//    /**
//     * 扫描路径
//     */
//    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};
//
//    /**
//     * 动态加载的类列表
//     */
//    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);
//
//    /**
//     * 加载所有类型
//     */
//    public static void loadAll() {
//        log.info("加载所有 SPI");
//        for (Class<?> aClass : LOAD_CLASS_LIST) {
//            load(aClass);
//        }
//    }
//
//    /**
//     * 获取某个接口的实例
//     *
//     * @param tClass
//     * @param key
//     * @param <T>
//     * @return
//     */
//    public static <T> T getInstance(Class<?> tClass, String key) {
//        String tClassName = tClass.getName();
//        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
//        if (keyClassMap == null) {
//            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
//        }
//        if (!keyClassMap.containsKey(key)) {
//            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
//        }
//        // 获取到要加载的实现类型
//        Class<?> implClass = keyClassMap.get(key);
//        // 从实例缓存中加载指定类型的实例
//        String implClassName = implClass.getName();
//        if (!instanceCache.containsKey(implClassName)) {
//            try {
//                instanceCache.put(implClassName, implClass.newInstance());
//            } catch (InstantiationException | IllegalAccessException e) {
//                String errorMsg = String.format("%s 类实例化失败", implClassName);
//                throw new RuntimeException(errorMsg, e);
//            }
//        }
//        return (T) instanceCache.get(implClassName);
//    }
//
//    /**
//     * 加载某个类型
//     *
//     * @param loadClass
//     * @throws IOException
//     */
//    public static Map<String, Class<?>> load(Class<?> loadClass) {
//        log.info("加载类型为 {} 的 SPI", loadClass.getName());
//        // 扫描路径，用户自定义的 SPI 优先级高于系统 SPI
//        Map<String, Class<?>> keyClassMap = new HashMap<>();
//        for (String scanDir : SCAN_DIRS) {
//            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
//            // 读取每个资源文件
//            for (URL resource : resources) {
//                try {
//                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        String[] strArray = line.split("=");
//                        if (strArray.length > 1) {
//                            String key = strArray[0];
//                            String className = strArray[1];
//                            keyClassMap.put(key, Class.forName(className));
//                        }
//                    }
//                } catch (Exception e) {
//                    log.error("spi resource load error", e);
//                }
//            }
//        }
//        loaderMap.put(loadClass.getName(), keyClassMap);
//        return keyClassMap;
//    }
//}
