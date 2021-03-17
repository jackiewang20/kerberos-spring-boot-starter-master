package com.example.kerberos.stater.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.example.kerberos.stater.configuration.KerberosProperties.PREFIX;

/**
 * @author jackie wang
 * @since 2021/2/24 11:50
 */
@ConfigurationProperties(PREFIX)
public class KerberosProperties {
    public static final String PREFIX = "spring.kerberos";

    /**
     * 是否开启认证
     */
    private Boolean authorization=false;

    /**
     * 认证类型：keytab/password
     */
    private String authType="keytab";

    /**
     * krb5.conf配置文件路径
     */
    private String kerberosConfigPath;

    /**
     * HADOOP_HOME环境变量路径
     */
    private String hadoopHome;

    /**
     * xxx.keytab文件路径
     */
    private String keytabFilePath;

    /**
     * kerberos主体用户
     */
    private String principalUsername;

    /**
     * kerberos主体用户密码
     */
    private String principalPassword;

    /**
     * 登录认证模块配置文件路径，配合kerberos主体账号、密码使用
     */
    private String authLoginModuleConfig;

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getKerberosConfigPath() {
        return kerberosConfigPath;
    }

    public void setKerberosConfigPath(String kerberosConfigPath) {
        this.kerberosConfigPath = kerberosConfigPath;
    }

    public String getHadoopHome() {
        return hadoopHome;
    }

    public void setHadoopHome(String hadoopHome) {
        this.hadoopHome = hadoopHome;
    }

    public String getKeytabFilePath() {
        return keytabFilePath;
    }

    public void setKeytabFilePath(String keytabFilePath) {
        this.keytabFilePath = keytabFilePath;
    }

    public String getPrincipalUsername() {
        return principalUsername;
    }

    public void setPrincipalUsername(String principalUsername) {
        this.principalUsername = principalUsername;
    }

    public String getPrincipalPassword() {
        return principalPassword;
    }

    public void setPrincipalPassword(String principalPassword) {
        this.principalPassword = principalPassword;
    }

    public String getAuthLoginModuleConfig() {
        return authLoginModuleConfig;
    }

    public void setAuthLoginModuleConfig(String authLoginModuleConfig) {
        this.authLoginModuleConfig = authLoginModuleConfig;
    }
}
