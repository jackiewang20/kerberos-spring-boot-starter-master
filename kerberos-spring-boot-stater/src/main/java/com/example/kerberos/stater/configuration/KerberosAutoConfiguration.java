package com.example.kerberos.stater.configuration;

import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;

import static com.example.kerberos.stater.configuration.KerberosProperties.PREFIX;

/**
 * @author jackie wang
 * @since 2021/2/24 11:51
 */
@Configuration
@EnableConfigurationProperties(KerberosProperties.class) // 激活配置属性
@Order(Integer.MIN_VALUE) // 客户端项目集成kerberos stater，需要优先初始化当前Bean进行kerberos链接认证
@ConditionalOnProperty(prefix = PREFIX, value = "authorization")
public class KerberosAutoConfiguration {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private KerberosProperties properties;

    /**
     * kerberos认证：keytab认证方式
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "authType", havingValue = "keytab")
    public String authByKeytab() throws IOException {
        // krb5.conf配置文件路径
        System.setProperty("java.security.krb5.conf", properties.getKerberosConfigPath());
        // HADOOP_HOME环境变量路径
        System.setProperty("hadoop.home.dir", properties.getHadoopHome());

        // kerberos keytab认证配置
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("hadoop.security.authentication", "kerberos");

        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab(properties.getPrincipalUsername(), properties.getKeytabFilePath());

        log.info("============ kerberos keytab认证成功 ============");
        return null;
    }

    /**
     * kerberos认证：password认证方式
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "authType", havingValue = "password")
    public String authByPassword() throws IOException, LoginException {
        // krb5.conf配置文件路径
        System.setProperty("java.security.krb5.conf", properties.getKerberosConfigPath());
        // HADOOP_HOME环境变量路径
        System.setProperty("hadoop.home.dir", properties.getHadoopHome());

        // kerberos密码认证配置
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("hadoop.security.authentication", "kerberos");
        configuration.set("java.security.auth.login.config", properties.getAuthLoginModuleConfig());

        UserGroupInformation.setConfiguration(configuration);
        LoginContext loginContext = new LoginContext("Client",
                new UserPassCallbackHandler(properties.getPrincipalUsername(), properties.getPrincipalPassword().toCharArray()));
        loginContext.login();

        UserGroupInformation.loginUserFromSubject(loginContext.getSubject());

        log.info("============ kerberos password认证成功 ============");
        return null;
    }

}
