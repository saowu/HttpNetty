package org.saowu.core.pojo;

public enum RequestMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    RequestMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
