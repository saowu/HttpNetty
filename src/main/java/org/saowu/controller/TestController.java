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

    @RequestMapping(method = RequestMethod.GET, path = "/files")
    public String test1(Map<String, Object> map) {
        List<Files> filesList = testService.selectFiles(map);
        return JSONObject.toJSONString(filesList);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/files")
    public String test2(Map<String, Object> map) {
        boolean b = testService.updateFiles(map);
        return JSONObject.toJSONString(b);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/files")
    public String test3(Map<String, Object> map) {
        boolean b = testService.deteleFiles(map);
        return JSONObject.toJSONString(b);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/files")
    public String test6(Map<String, Object> map) {
        boolean b = testService.insertFiles(map);
        return JSONObject.toJSONString(b);
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
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    public String test(Map<String, Object> map) {
        HashMap<String, String> fileInfo = new HashMap<>();
        for (String key : map.keySet()) {
            //获取文件对象并保存
            String path = IOUtils.saveFileUpload(key, map.get(key));
            fileInfo.put(key, path);
        }
        return JSONObject.toJSONString(fileInfo);
    }

}
