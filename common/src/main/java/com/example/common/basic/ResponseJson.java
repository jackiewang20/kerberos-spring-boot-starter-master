package com.example.common.basic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;

/**
 * @author jackie
 * @Title: ResponseJson
 * @Description: 输出json对象POJO类；
 * @date 2018/11/19 15:04
 */
public class ResponseJson {

    private String errorCode;
    private String errorMsg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    /**
     * 初始化构造方法ok
     */
    public ResponseJson(){
        this.errorCode = EnumCode.CODE_OK.getCode();
        this.errorMsg = EnumCode.CODE_OK.getText();
    }

    public ResponseJson(Object data){
        this.errorCode = EnumCode.CODE_OK.getCode();
        this.errorMsg = EnumCode.CODE_OK.getText();
        this.data = data;
    }

    public ResponseJson(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ResponseJson(String errorCode, String errorMsg, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
