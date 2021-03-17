package com.example.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.common.basic.EnumCode;
import com.example.common.basic.ResponseJson;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author jackie
 * @Title: ExceptionHandler
 * @date 2018/12/17 16:00
 */
@ControllerAdvice
public class ExceptionHandle {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * API请求返回400错误，一般是客户端传递无效的参数报错
     *
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    @ResponseBody
    public String handleBadRequest(Exception e) {
        ResponseJson errJson = new ResponseJson();
        errJson.setErrorCode(EnumCode.CODE_PARAMETER_INVALID.getCode());
        errJson.setErrorMsg(EnumCode.CODE_PARAMETER_INVALID.getText());
        errJson.setData(e.getCause() == null ? e.toString() : e.getCause());
        return JSON.toJSONString(errJson);
    }

    /**
     * hibernate validater参数校验异常处理：生产环境放入bean注解类全局使用；
     * 详细参考util包中的全局异常处理类ExceptionHandle.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Object handleBindException(BindException ex) {
        HashMap<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorMap;
    }


    public ResponseJson mybatisException(Exception e, ResponseJson responseJson) {
        // 使用反射判断MyBatisSystemException类是否存在
        try {
            Class<?> aClass = Class.forName("org.mybatis.spring.MyBatisSystemException");

            if(e instanceof MyBatisSystemException) {
                if (e.getCause() != null && e.getCause().getMessage().contains("java.sql.SQLException")) {
                    responseJson.setErrorCode(EnumCode.CODE_DB_CONNECTION_TIMED.getCode());
                    responseJson.setErrorMsg(EnumCode.CODE_DB_CONNECTION_TIMED.getText());
                    log.error("数据库链接超时异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
                } else {
                    responseJson.setErrorCode(EnumCode.CODE_DB_EXECUTION_EXCEPTION.getCode());
                    responseJson.setErrorMsg(EnumCode.CODE_DB_EXECUTION_EXCEPTION.getText());
                    log.error("数据库执行异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
                }
            }
        } catch (ClassNotFoundException classNotFoundException) {
        }

        return responseJson;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String handle(Exception e) {
        ResponseJson responseJson = new ResponseJson();

        ResponseJson responseJson1 = mybatisException(e, responseJson);
        if(!responseJson1.getErrorCode().equals(EnumCode.CODE_OK.getCode())) {
            return JSON.toJSONString(responseJson);
        }

        if (e instanceof JSONException) {
            responseJson.setErrorCode(EnumCode.CODE_JSON_EXCEPTION.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_JSON_EXCEPTION.getText());
            log.error("json异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof NullPointerException) {
            responseJson.setErrorCode(EnumCode.CODE_PARAMETER_MISSING.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_PARAMETER_MISSING.getText() + e.getMessage());
            log.error("空指针异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof IllegalArgumentException) {
            responseJson.setErrorCode(EnumCode.CODE_PARAMETER_INVALID.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_PARAMETER_INVALID.getText() + e.getMessage());
            log.error("参数无效异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof FileNotFoundException) {
            responseJson.setErrorCode(EnumCode.CODE_NOT_FOUND_FILE.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_NOT_FOUND_FILE.getText() + e.getMessage());
        } else if (e instanceof IOException) {
            responseJson.setErrorCode(EnumCode.CODE_IO_EXCEPTION.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_IO_EXCEPTION.getText() + e.getMessage());
            log.error("文件操作异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof RuntimeException) {
            responseJson.setErrorCode(EnumCode.CODE_RUNTIME_EXCEPTION.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_RUNTIME_EXCEPTION.getText() + e.getMessage());
            log.error("运行时异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            responseJson.setErrorCode(EnumCode.CODE_WEB_REQUEST_NOT_SUPPORTED.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_WEB_REQUEST_NOT_SUPPORTED.getText() + e.getMessage());
            log.error("HTTP请求方法协议不支持异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else {
            responseJson.setErrorCode(EnumCode.CODE_EXCEPTION.getCode());
            responseJson.setErrorMsg(EnumCode.CODE_EXCEPTION.getText());
            log.error("服务异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        }
        return JSON.toJSONString(responseJson);
    }

}