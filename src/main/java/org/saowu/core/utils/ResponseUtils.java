package org.saowu.core.utils;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.saowu.core.pojo.Template;

public class ResponseUtils {
    /**
     * 封装返回信息
     *
     * @param object
     * @return
     */
    public static FullHttpResponse getFullHttpResponse(Object object) {
        String message = "";
        FullHttpResponse fullHttpResponse = null;

        if (object instanceof HttpResponseStatus) {
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, (HttpResponseStatus) object);
        } else {
            if (object instanceof String) {
                message = (String) object;
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
            } else if (object instanceof Template) {
                message = ((Template) object).getHtml_text();
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            }else if (object instanceof Template) {
                message = ((Template) object).getHtml_text();
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            }
        }
        return fullHttpResponse;
    }

}
