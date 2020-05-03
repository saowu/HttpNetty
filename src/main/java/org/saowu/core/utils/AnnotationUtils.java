package org.saowu.core.utils;

import org.saowu.core.annotation.Controller;
import org.saowu.core.annotation.RequestMapping;
import org.saowu.core.config.ContextConfig;
import org.saowu.core.pojo.RequestMethod;
import org.saowu.core.pojo.RouteInfo;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;


public class AnnotationUtils {

    public AnnotationUtils() {
        //扫描目录
        scanAnnotations("org.saowu");
    }

    /**
     * 扫描注解，并put到上下文
     *
     * @param pkg
     */
    private void scanAnnotations(final String pkg) {
        String pkgDir = pkg.replaceAll("\\.", "/");
        URL url = getClass().getClassLoader().getResource(pkgDir);
        File file = new File(url.getFile());
        File files[] = file.listFiles(_file -> {
            String file_name = _file.getName();
            if (_file.isDirectory()) {
                scanAnnotations(pkg + "." + file_name);
            } else {
                //判断文件后缀是否为.
                if (file_name.endsWith(".class")) {
                    return true;
                }
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
                    // 反射得到目标类的所有方法
                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            //方法上的注解内容提取
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            RequestMethod request_type = annotation.method();
                            String request_path = annotation.path();
                            //put mappingMap
                            if (controller.path().isEmpty()) {
                                ContextConfig.routesMap.put(request_path, new RouteInfo(pkgCls, method.getName(), request_type));
                            } else {
                                ContextConfig.routesMap.put(controller.path() + request_path, new RouteInfo(pkgCls, method.getName(), request_type));
                            }
                        }
                    }
                    ContextConfig.beanMap.put(pkgCls, aClass.getDeclaredConstructor().newInstance());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
