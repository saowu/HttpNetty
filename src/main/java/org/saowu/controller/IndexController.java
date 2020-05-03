package org.saowu.controller;

import com.alibaba.fastjson.JSONObject;
import org.saowu.core.annotation.Controller;
import org.saowu.core.annotation.RequestMapping;
import org.saowu.core.pojo.RequestMethod;
import org.saowu.core.pojo.Template;

import java.util.Map;

@Controller(path = "/index")
public class IndexController {

    @RequestMapping(method = RequestMethod.POST, path = "/test")
    public String test(Map<String, String> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/test1")
    public String test1(Map<String, String> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/test2")
    public String test2(Map<String, String> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/test3")
    public String test3(Map<String, String> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/test4")
    public Template test4(Map<String, String> map) {
        return new Template("index.html");
    }
}
