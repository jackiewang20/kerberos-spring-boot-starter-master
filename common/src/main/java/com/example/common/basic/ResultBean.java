package com.example.common.basic;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author jackie wang
 * @since 2021/3/10 17:00
 */
public class ResultBean<T> {
    private String errorCode;
    private String errorMsg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 初始化构造方法ok
     */
    public ResultBean(){
        this.errorCode = EnumCode.CODE_OK.getCode();
        this.errorMsg = EnumCode.CODE_OK.getText();
    }

    public ResultBean(T data){
        this.errorCode = EnumCode.CODE_OK.getCode();
        this.errorMsg = EnumCode.CODE_OK.getText();
        this.data = data;
    }

    public ResultBean(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ResultBean(String errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseJson{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

}
