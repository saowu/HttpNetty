package org.saowu.core.server;

import org.saowu.core.config.ContextConfig;
import org.saowu.core.utils.AnnotationUtils;
import org.saowu.core.utils.IOUtils;

/**
 * 初始化中心
 */
public class InitCenter {
    public InitCenter(int port) {
        //init dir
        IOUtils.isChartPathExist(ContextConfig.TEMPLATES);
        IOUtils.isChartPathExist(ContextConfig.STATIC);
        IOUtils.isChartPathExist(ContextConfig.UPLOAD);
        //打印banner
        IOUtils.bannerRead();
        //扫描注解
        new AnnotationUtils("org.saowu");

        System.err.println("Http Netty : http://127.0.0.1:" + port);
    }
}
