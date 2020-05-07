package org.saowu.core.server;

import org.saowu.core.config.ContextConfig;
import org.saowu.core.utils.AnnotationUtils;
import org.saowu.core.utils.IOUtils;

/**
 * 初始化中心
 */
public class InitCenter {
    public InitCenter(int port) {
        //init path
        IOUtils.isChartPathExist(ContextConfig.TEMPLATES);
        IOUtils.isChartPathExist(ContextConfig.STATIC);
        IOUtils.isChartPathExist(ContextConfig.UPLOAD);
        //扫描注解
        new AnnotationUtils("org.saowu");
        //打印banner
        IOUtils.bannerRead();
        System.err.println("Netty Server : http://127.0.0.1:" + port);
    }
}
