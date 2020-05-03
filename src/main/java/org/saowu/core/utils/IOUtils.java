package org.saowu.core.utils;

import org.saowu.core.annotation.Controller;
import org.saowu.core.annotation.RequestMapping;
import org.saowu.core.config.ContextConfig;
import org.saowu.core.pojo.RequestMethod;

import java.io.*;

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
        File file = new File(System.getProperty("user.dir") + ContextConfig.TEMPLATES + template_name);
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

}
