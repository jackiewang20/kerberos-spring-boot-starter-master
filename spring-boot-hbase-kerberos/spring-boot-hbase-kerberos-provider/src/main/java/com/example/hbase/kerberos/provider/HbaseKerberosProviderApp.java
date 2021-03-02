package com.example.hbase.kerberos.provider;

import com.example.kerberos.stater.configuration.KerberosObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jackie wang
 * @since 2021/2/24 13:58
 */
@SpringBootApplication(scanBasePackages = {"com.example.hbase.kerberos.provider"})
public class HbaseKerberosProviderApp {
    @Autowired
    private KerberosObject kerberosObject;

    public static void main(String[] args) {
        SpringApplication.run(HbaseKerberosProviderApp.class, args);
    }

}
