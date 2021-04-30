package com.iscdemo.models.basemodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscdemo.models.constant.ErrorConstant;

import java.io.Serializable;

public class InvocationContext<T> implements Serializable {
    // as default code. it's will be changed if something unusual happened.
    private int errorCode = ErrorConstant.OPERATION_SUCCESS;

    //as default message. it's will be changed if something unusual happened.
    private String errorMessage = ErrorConstant.OPERATION_SUCCESS_MESSAGE;
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
