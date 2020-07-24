package com.platform.util;

/**
 * <p>
 * 为官网打通增加返回错误状态码的封装。
 * 
 * @author JZ
 * @param <T>
 */
public class HttpJsonResultForHaier<T> extends HttpJsonResult<T> {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6911202137274032506L;

    public HttpJsonResultForHaier() {
    }

    public HttpJsonResultForHaier(T data) {
        super.setData(data);
    }

    public void setErrorInfo(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.setMessage(errorMessage);
    }

    private String code; //返回的错误状态码

    public String getCode() {
        return this.code;
    }
}
