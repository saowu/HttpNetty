package org.saowu.core.pojo;

public class RouteInfo {
    //类全路径名
    public String class_name;
    //方法名
    public String method_name;
    //请求类型
    public RequestMethod request_type;


    public RouteInfo(String class_name, String method_name, RequestMethod request_type) {
        this.class_name = class_name;
        this.method_name = method_name;
        this.request_type = request_type;
    }
}
