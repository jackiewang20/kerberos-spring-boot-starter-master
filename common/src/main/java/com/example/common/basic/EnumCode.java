package com.example.common.basic;

public enum EnumCode {
    /**
     * 服务端异常
     */
    CODE_EXCEPTION("Server exception.", "-1"),
    /**
     * 服务器请求正常
     */
    CODE_OK("ok", "0"),
    /**
     * 服务不可用
     */
    CODE_UNAVAILABLE("Service unavailable.", "1"),
    /**
     * Json格式无效
     */
    CODE_JSON_EXCEPTION("JSON exception.", "2"),
    /**
     * 参数无效
     */
    CODE_PARAMETER_INVALID("Parameter is invalid.", "3"),

    /**
     * 非法访问
     */
    CODE_UNAUTHORIZED("Unauthorized.", "4"),

    /**
     * token无效
     */
    CODE_TOKEN_INVALID("Token is invalid.", "5"),

    /**
     * sign无效
     */
    CODE_SIGN_INVALID("Sign is invalid.", "6"),

    /**
     * 请求过多
     */
    CODE_TO_MANY_REQUESTS("To many Requests.", "7"),

    /**
     * 参数缺失
     */
    CODE_PARAMETER_MISSING("Parameter is missing.", "8"),

    /**
     * 数据库连接超时
     */
    CODE_DB_CONNECTION_TIMED("Database Connection timed out.Check that the database connection string is configured correctly and that the DB is started.", "9"),

    /**
     * 数据库执行异常
     */
    CODE_DB_EXECUTION_EXCEPTION("Database execution exception.", "10"),

    /**
     * 文件操作异常
     */
    CODE_IO_EXCEPTION("File operation exception.", "11"),

    /**
     * 404
     */
    CODE_NOT_FOUND_PATH("Not found path.", "404"),

    /**
     * 405
     */
    CODE_NOT_FOUND_FILE("File not found.", "405"),

    /**
     * 406
     */
    CODE_NOT_FOUND_DIR("Directory not found.", "406");

    private String text;
    private String code = "0";

    private EnumCode(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }

}