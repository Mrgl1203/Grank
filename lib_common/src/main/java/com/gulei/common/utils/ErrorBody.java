package com.gulei.common.utils;

/**
 * Created by gl152 on 2018/6/15.
 */

public class ErrorBody {
    public static final String Default_errorMsg = "获取数据失败";
    public static final String NetWork_errorMsg = "网络异常";
    private int errorCode;

    private String errorMessage;


    public ErrorBody(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorBody(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
}
