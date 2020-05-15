package org.saowu.core.utils;

import org.saowu.core.annotation.*;
import org.saowu.core.config.ApplicationContext;
import org.saowu.core.pojo.RequestMethod;
import org.saowu.core.pojo.RouteInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;


public class AnnotationUtils {
    //待注入字段 字段所属类全路径:字段对象
    public Map<String, Field> fields = new HashMap<>();

    public AnnotationUtils(String pkg) {
        //扫描目录
        scanAnnotations(pkg);
        System.err.println("Http Netty : Annotation scanning completed");
        //依赖注入
        injectionBean();
        System.err.println("Http Netty : Annotation injection completed");

    }

    /**
     * 扫描注解，并存到上下文
     *
     * @param pkg
     */
    private void scanAnnotations(final String pkg) {
        String pkgDir = pkg.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(pkgDir);
        File file = new File(url.getFile());
        File[] files = file.listFiles(_file -> {
            String file_name = _file.getName();
            if (_file.isDirectory()) {
                scanAnnotations(pkg + "." + file_name);
            } else {
                //判断文件后缀是否为.
                return file_name.endsWith(".class");
            }
            return false;
        });

        for (File _file : files) {
            String file_name = _file.getName();
            //去除.class以后的文件名
            file_name = file_name.substring(0, file_name.lastIndexOf("."));
            //将名字的第一个字母转为小写(用它作为key存储map)
            String key = String.valueOf(file_name.charAt(0)).toLowerCase() + file_name.substring(1);
            //构建一个类全名(包名.类名)
            String pkgCls = pkg + "." + file_name;
            try {
                //反射构建对象
                Class<?> aClass = Class.forName(pkgCls);
                //判定类上是否有注解isAnnotationPresent()
                if (aClass.isAnnotationPresent(Controller.class)) {
                    Controller controller = aClass.getAnnotation(Controller.class);
                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            //方法上的注解内容提取
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            RequestMethod request_type = annotation.method();
                            String request_path = controller.path() + annotation.path();
                            Map<String, RouteInfo> stringRouteInfoMap = ApplicationContext.routeMap.get(request_type.getMethod());
                            //put mappingMap
                            if (!stringRouteInfoMap.containsKey(request_path)) {
                                stringRouteInfoMap.put(request_path, new RouteInfo(pkgCls, method.getName()));
                            } else {
                                throw new RuntimeException("Route already exists");
                            }
                        }
                    }
                }
                //获取待注入字段并存放到集合
                if (aClass.isAnnotationPresent(Service.class) || aClass.isAnnotationPresent(Repository.class) || aClass.isAnnotationPresent(Controller.class) || aClass.isAnnotationPresent(Component.class)) {
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field field : declaredFields) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            fields.put(pkgCls, field);
                        }
                    }
                    ApplicationContext.beanMap.put(pkgCls, aClass.getDeclaredConstructor().newInstance());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 注入bean
     */
    private void injectionBean() {
        for (Map.Entry<String, Field> fieldEntry : fields.entrySet()) {
            Field field = fieldEntry.getValue();
            field.setAccessible(true);
            try {
                Object parent_object = ApplicationContext.beanMap.get(fieldEntry.getKey());
                Object field_proxy = ApplicationContext.beanMap.get(field.getType().getName());
                //注入bean
                field.set(parent_object, field_proxy);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
