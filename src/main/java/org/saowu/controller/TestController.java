package org.saowu.controller;

import com.alibaba.fastjson.JSONObject;
import org.saowu.Application;
import org.saowu.core.annotation.Autowired;
import org.saowu.core.annotation.Controller;
import org.saowu.core.annotation.RequestMapping;
import org.saowu.core.config.ApplicationContext;
import org.saowu.core.pojo.RequestMethod;
import org.saowu.core.pojo.Template;
import org.saowu.core.utils.IOUtils;
import org.saowu.entity.Files;
import org.saowu.service.TestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(method = RequestMethod.GET, path = "/test1")
    public String test1(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/test2")
    public String test2(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/test3")
    public String test3(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public Template test4(Map<String, Object> map) {
        return new Template("index.html");
    }

    /**
     * 文件上传
     *
     * @param map filename ：FileUpload
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/test")
    public String test(Map<String, Object> map) {
        HashMap<String, String> fileInfo = new HashMap<>();
        for (String key : map.keySet()) {
            //获取文件对象并保存
            String path = IOUtils.saveFileUpload(key, map.get(key));
            fileInfo.put(key, path);
        }
        return JSONObject.toJSONString(fileInfo);
    }

    /**
     * 数据库连接
     *
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/dbtest")
    public String test5(Map<String, Object> map) {
        for (String key:ApplicationContext.beanMap.keySet()) {
            System.out.println(key);
        }
        List<Files> filesList = testService.selectAll();
        return JSONObject.toJSONString(filesList);
    }
}
