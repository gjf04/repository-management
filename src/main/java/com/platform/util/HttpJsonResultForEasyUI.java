package com.platform.util;

public class HttpJsonResultForEasyUI<T> extends HttpJsonResult<T> {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8036273323046245008L;

    public HttpJsonResultForEasyUI(T rows, String csrfToken) {
        setData(rows);
        this.csrfToken = csrfToken;
    }

    private String csrfToken = "";

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

}
