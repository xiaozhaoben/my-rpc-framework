package top.zhao.rpc.factory;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂
 * @author xiaozhao
 */
@Slf4j
public class SingletonFactory {

    private static Map<Class, Object> objectMap = new HashMap<>();

    private SingletonFactory() {}

    public static <T> T getInstance(Class<T> clazz){
        Object o = objectMap.get(clazz);
        if (o == null){
            synchronized (clazz){
                if (o == null){
                    try {
                        o = clazz.newInstance();
                        objectMap.put(clazz, o);
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.error("创建实例发生错误");
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return clazz.cast(o);
    }

}
