package com.example.kerberos.stater.configuration;

/**
 * @author jackie wang
 * @since 2021/3/2 19:43
 */
public class KerberosObject {

    private KerberosProperties properties;
    private Boolean isAuth = false;

    public KerberosObject(KerberosProperties properties) {
        this.properties = properties;
        if (properties != null) {
            isAuth = true;
        }
    }

    /**
     * 认证属性
     * @return
     */
    public KerberosProperties getProperties() {
        return properties;
    }

    /**
     * 是否成功认证
     * @return
     */
    public Boolean getAuth() {
        return isAuth;
    }

}
