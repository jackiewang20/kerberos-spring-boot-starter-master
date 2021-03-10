package com.example.hdfs.kerberos.provider;

import com.example.kerberos.stater.configuration.KerberosObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jackie wang
 * @since 2021/2/24 14:02
 */
@SpringBootApplication
public class HdfsKerberosProviderApp {
    @Autowired
    private KerberosObject kerberosObject;

    public static void main(String[] args) {
        SpringApplication.run(HdfsKerberosProviderApp.class, args);
    }

}
