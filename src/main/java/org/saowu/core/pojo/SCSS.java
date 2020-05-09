package org.saowu.core.pojo;

import org.saowu.core.utils.IOUtils;

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
