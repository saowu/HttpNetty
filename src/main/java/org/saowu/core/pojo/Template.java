package org.saowu.core.pojo;

import org.saowu.core.utils.IOUtils;

public class Template {

    private String html_text;

    public Template(String html_name) {
        if (html_name != null) {
            this.html_text = IOUtils.templateRead(html_name);
        }
    }

    public String getHtml_text() {
        return html_text;
    }
}
