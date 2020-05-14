package org.saowu.core.config;

import org.saowu.core.pojo.RouteInfo;
import org.saowu.core.utils.PoolUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ContextConfig {
    //路由 mapping
    public static final Map<String, RouteInfo> routesMap = new HashMap<>();
    //Controller object
    public static final Map<String, Object> beanMap = new HashMap<>();
    //模板目录
    public static final String TEMPLATES = System.getProperty("user.dir") + "/src/main/resources/templates/";
    //静态文件目录
    public static final String STATIC = System.getProperty("user.dir") + "/src/main/resources/static/";
    //上传文件目录
    public static final String UPLOAD = System.getProperty("user.dir") + "/src/main/resources/upload/";
    //连接池对象
    public static final PoolUtils poolUtils = new PoolUtils();
}
