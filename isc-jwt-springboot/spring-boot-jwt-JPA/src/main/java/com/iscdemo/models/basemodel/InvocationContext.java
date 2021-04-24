package com.iscdemo.models.basemodel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class InvocationContext<T> implements Serializable {
    private int errorCode;
    private String errorMessage;
    private T data;

    public InvocationContext() {

    }

    public InvocationContext(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public InvocationContext(T data, int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccessful() {
        return (errorCode == 0) ? true : false;
    }


}
