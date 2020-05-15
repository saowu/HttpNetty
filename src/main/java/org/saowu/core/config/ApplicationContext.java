package org.saowu.core.config;

import org.saowu.core.pojo.RouteInfo;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    //路由<String, Map<String, RouteInfo>>
    public static final Map<String, Map<String, RouteInfo>> routeMap = new HashMap<String, Map<String, RouteInfo>>() {
        {
            put("GET", new HashMap<>());
            put("PUT", new HashMap<>());
            put("POST", new HashMap<>());
            put("DELETE", new HashMap<>());
        }
    };
    //beans<全路径名,对象>
    public static final Map<String, Object> beanMap = new HashMap<>();
    //模板目录
    public static final String TEMPLATES = System.getProperty("user.dir") + "/src/main/resources/templates/";
    //静态文件目录
    public static final String STATIC = System.getProperty("user.dir") + "/src/main/resources/static/";
    //上传文件目录
    public static final String UPLOAD = System.getProperty("user.dir") + "/src/main/resources/upload/";
}
