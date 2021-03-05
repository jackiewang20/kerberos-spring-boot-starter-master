package com.example.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.common.basic.ResponseJson;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        errJson.setErrorCode(ResponseJson.EnumCode.CODE_PARAMETER_INVALID.getCode());
        errJson.setErrorMsg(ResponseJson.EnumCode.CODE_PARAMETER_INVALID.getText());
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

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String handle(Exception e) {
        ResponseJson responseJson = new ResponseJson();
        if (e instanceof JSONException) {
            responseJson.setErrorCode(ResponseJson.EnumCode.CODE_JSON_EXCEPTION.getCode());
            responseJson.setErrorMsg(ResponseJson.EnumCode.CODE_JSON_EXCEPTION.getText());
            log.error("json异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        } else if (e instanceof MyBatisSystemException) {
            if (e.getCause() != null && e.getCause().getMessage().contains("java.sql.SQLException")) {
                responseJson.setErrorCode(ResponseJson.EnumCode.CODE_DB_CONNECTION_TIMED.getCode());
                responseJson.setErrorMsg(ResponseJson.EnumCode.CODE_DB_CONNECTION_TIMED.getText());
                log.error("数据库链接异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
            } else {
                responseJson.setErrorCode(ResponseJson.EnumCode.CODE_DB_EXECUTION_EXCEPTION.getCode());
                responseJson.setErrorMsg(ResponseJson.EnumCode.CODE_DB_EXECUTION_EXCEPTION.getText());
                log.error("数据库执行异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
            }
        } else {
            responseJson.setErrorCode(ResponseJson.EnumCode.CODE_EXCEPTION.getCode());
            responseJson.setErrorMsg(ResponseJson.EnumCode.CODE_EXCEPTION.getText());
            log.error("服务异常:{},详细信息:", e.getCause() == null ? e.toString() : e.getCause(), e);
        }
        return JSON.toJSONString(responseJson);
    }

}