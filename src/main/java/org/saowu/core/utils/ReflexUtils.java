package org.saowu.core.utils;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.saowu.core.config.ApplicationContext;
import org.saowu.core.pojo.RouteInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 反射工具类
 */
public class ReflexUtils {
    /**
     * 利用反射调用api
     *
     * @param path         api
     * @param request_type 请求方式
     * @param map          请求数据
     * @return
     */
    public static Object call(String path, String request_type, Map<String, Object> map) {
        if (ApplicationContext.routeMap.get(request_type).containsKey(path)) {
            try {
                RouteInfo classFullPath = ApplicationContext.routeMap.get(request_type).get(path);
                Object object = ApplicationContext.beanMap.get(classFullPath.class_name);
                Method method = object.getClass().getDeclaredMethod(classFullPath.method_name, Map.class);
                return method.invoke(object, map);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            return HttpResponseStatus.NOT_FOUND;
        }
    }
}
