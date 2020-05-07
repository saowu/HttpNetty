package org.saowu.core.utils;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.*;
import org.saowu.core.config.ContextConfig;

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
    public static String parseRequestData(FullHttpRequest request, Map<String, Object> params) throws IOException {
        String method = request.method().name();
        //处理中文乱码
        String uri = URLDecoder.decode(request.uri(), "UTF-8");

        if ("GET".equals(method) || "DELETE".equals(method)) {
            //URI解析
            QueryStringDecoder queryDecoder = new QueryStringDecoder(uri);
            Map<String, List<String>> parameters = queryDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
                params.put(attr.getKey(), attr.getValue().get(0));
            }
            uri = queryDecoder.path();
        } else if ("POST".equals(method) || "PUT".equals(method)) {
            String raw = request.headers().get("Content-Type");
            if ("application/x-www-form-urlencoded".equals(raw)) {
                //表单数据解析
                String jsonStr = request.content().toString(StandardCharsets.UTF_8);
                QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
                Map<String, List<String>> uriAttributes = queryDecoder.parameters();
                for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                    params.put(attr.getKey(), attr.getValue());
                }
            } else if ("application/json".equals(raw)) {
                //json数据解析
                String jsonStr = request.content().toString(StandardCharsets.UTF_8);
                Map<String, Object> map = JSON.parseObject(jsonStr, params.getClass());
                for (Map.Entry<String, Object> attr : map.entrySet()) {
                    params.put(attr.getKey(), attr.getValue());
                }
            } else if ("multipart/form-data".equals(raw.split(";")[0])) {
                //表单文件解析,postman测试失败
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
                List<InterfaceHttpData> bodyHttpDatas = decoder.getBodyHttpDatas();
                for (InterfaceHttpData data : bodyHttpDatas) {
                    if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                        FileUpload fileUpload = (FileUpload) data;
                        String filename = fileUpload.getFilename();
                        if (fileUpload.isCompleted()) {
                            StringBuffer fileNameBuf = new StringBuffer();
                            fileNameBuf.append(IOUtils.isChartPathExist(ContextConfig.UPLOAD)).append(filename);
                            fileUpload.renameTo(new File(fileNameBuf.toString()));
                        }
                    }
                }
            } else {
                System.err.println("Not Resolve Content-Type:" + raw);
            }
            uri = request.uri().split("[?=&]+")[0];
        }
        return uri;
    }
}
