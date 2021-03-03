package com.example.hbase.kerberos.provider.init;

import com.example.kerberos.stater.configuration.KerberosObject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jackie wang
 * @since 2021/3/2 20:26
 */
public class InitBean implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("================程序销毁==================");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("================程序初始化==================");

    }
}
