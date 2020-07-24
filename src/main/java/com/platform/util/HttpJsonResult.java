package com.platform.util;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public class HttpJsonResult<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8637111820477625638L;

    public HttpJsonResult() {

    }

    public HttpJsonResult(T data) {
        this.data = data;
    }

    public HttpJsonResult(String errorMessage) {
        this.success = false;
        this.message = errorMessage;
    }

    private Boolean success = true;

    public Boolean getSuccess() {
        return this.success;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.success = false;
        this.message = message;
    }

    private Integer totalCount = 0;

    public void setTotalCount(Integer count) {
        this.totalCount = count;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }
}