package org.saowu.core.config;

import org.saowu.core.pojo.RouteInfo;

import java.util.HashMap;
import java.util.Map;

public class ContextConfig {
    //路由 mapping
    public static final Map<String, RouteInfo> routesMap = new HashMap<>();
    //Controller object
    public static final Map<String, Object> beanMap = new HashMap<>();
    //模板目录
    public static final String TEMPLATES = "/src/main/resources/templates/";
    //静态文件目录
    public static final String STATIC = "/src/main/resources/static/";
}
