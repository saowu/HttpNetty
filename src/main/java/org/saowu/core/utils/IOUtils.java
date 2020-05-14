package org.saowu.core.utils;

import io.netty.handler.codec.http.multipart.FileUpload;
import org.saowu.core.annotation.Controller;
import org.saowu.core.annotation.RequestMapping;
import org.saowu.core.config.ApplicationContext;
import org.saowu.core.pojo.RequestMethod;
import org.saowu.core.pojo.SCSS;
import org.saowu.core.pojo.SIMG;
import org.saowu.core.pojo.SJS;

import java.io.*;
import java.util.Map;

@Controller
public class IOUtils {

    /**
     * 输出banner
     */
    public static void bannerRead() {
        File file = new File(System.getProperty("user.dir") + "/src/main/java/org/saowu/core/banner");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                System.err.println(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取template
     */
    public static String templateRead(String template_name) {
        File file = new File(ApplicationContext.TEMPLATES + template_name);
        FileReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    /**
     * 读取static css
     */
    @RequestMapping(method = RequestMethod.GET, path = "/static/css")
    public SCSS cssRead(Map<String, Object> map) {
        File file = new File(ApplicationContext.STATIC + map.get("file"));
        FileReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SCSS(stringBuffer.toString());
    }


    /**
     * 读取static img
     */
    @RequestMapping(method = RequestMethod.GET, path = "/static/img")
    public SIMG imgRead(Map<String, Object> map) {
        File file = new File(ApplicationContext.STATIC + map.get("file"));
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            return new SIMG(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SIMG(null);
    }

    /**
     * 读取static js
     */
    @RequestMapping(method = RequestMethod.GET, path = "/static/js")
    public SJS jsRead(Map<String, Object> map) {
        File file = new File(ApplicationContext.STATIC + map.get("file"));
        FileReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SJS(stringBuffer.toString());
    }

    /**
     * 判断文件夹是否存在
     *
     * @param dirPath
     * @return
     */
    public static String isChartPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }

    /**
     * saveFileUpload
     *
     * @param filename
     * @param object   FileUpload
     * @return filepath
     */
    public static String saveFileUpload(String filename, Object object) {
        if (filename.isEmpty()) {
            return null;
        }
        try {
            if (object instanceof FileUpload) {
                FileUpload fileUpload = (FileUpload) object;
                if (fileUpload.isCompleted()) {
                    StringBuffer fileNameBuf = new StringBuffer();
                    fileNameBuf.append(IOUtils.isChartPathExist(ApplicationContext.UPLOAD)).append(filename);
                    fileUpload.renameTo(new File(fileNameBuf.toString()));
                    return fileNameBuf.toString();
                } else {
                    throw new RuntimeException("object not is Completed");
                }
            } else {
                throw new RuntimeException("object not instanceof FileUpload");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
