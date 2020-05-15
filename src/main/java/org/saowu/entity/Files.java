package org.saowu.entity;

import org.saowu.core.annotation.Column;
import org.saowu.core.annotation.Table;

@Table(name = "t_files")
public class Files {
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
