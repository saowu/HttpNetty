package org.saowu.core.utils;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
public class HttpRequestUtils {
    /**
     * 解析http请求数据
     *
     * @param request request信息
     * @param params  请求参数
     * @return uri
     * @throws IOException
     */
    public static String parseRequestData(FullHttpRequest request, Map<String, String> params) throws IOException {
        String method = request.method().name();
        //处理中文乱码
        String uri = URLDecoder.decode(request.uri(), "UTF-8");

        if ("GET".equals(method) || "DELETE".equals(method)) {
            //url数据解析
            String[] data = uri.split("[?=&]+");
            for (int i = 1; i < data.length; i += 2) {
                params.put(data[i], data[i + 1]);
            }
            uri = data[0];
        } else if ("POST".equals(method) || "PUT".equals(method)) {
            String raw = request.headers().get("Content-Type");
            if ("multipart/form-data".equals(raw.split(";")[0])) {
                //表单数据解析
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
                List<InterfaceHttpData> paramList = decoder.offer(request).getBodyHttpDatas();
                for (InterfaceHttpData parm : paramList) {
                    Attribute data = (Attribute) parm;
                    params.put(data.getName(), data.getValue());
                }
            } else if ("application/json".equals(raw)) {
                //json数据解析
                String json = request.content().toString(StandardCharsets.UTF_8);
                Map<String, String> map = JSON.parseObject(json, params.getClass());
                for (String key : map.keySet()) {
                    params.put(key, map.get(key));
                }
            } else {
                System.err.println("Not Resolve Content-Type:" + raw);
            }
            uri = request.uri();
        }
        return uri;
    }
}
