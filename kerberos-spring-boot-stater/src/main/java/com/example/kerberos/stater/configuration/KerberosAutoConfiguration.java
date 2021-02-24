package com.example.kerberos.stater.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author jackie wang
 * @since 2021/2/24 11:51
 */
@Configuration
@EnableConfigurationProperties(KerberosProperties.class) // 激活配置属性
@Order(Integer.MIN_VALUE) // 客户端项目集成kerberos stater，需要优先初始化当前Bean进行kerberos链接认证
public class KerberosAutoConfiguration {



}
