package org.saowu.core.pojo;

public class RouteInfo {
    //类全路径名
    public String class_name;
    //方法名
    public String method_name;


    public RouteInfo(String class_name, String method_name) {
        this.class_name = class_name;
        this.method_name = method_name;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "class_name='" + class_name + '\'' +
                ", method_name='" + method_name + '\'' +
                '}';
    }
}
