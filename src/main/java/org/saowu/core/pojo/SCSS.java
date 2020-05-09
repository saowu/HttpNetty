package org.saowu.core.pojo;

/**
 * css文件
 */
public class SCSS {
    private String text;

    public SCSS(String html_name) {
        if (html_name != null) {
            this.text = html_name;
        }
    }

    public String getText() {
        return text;
    }
}
