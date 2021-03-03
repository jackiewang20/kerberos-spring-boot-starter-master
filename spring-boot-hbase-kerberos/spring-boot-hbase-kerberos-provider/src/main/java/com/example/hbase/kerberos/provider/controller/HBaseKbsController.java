package com.example.hbase.kerberos.provider.controller;

import com.example.hbase.stater.component.HBaseService;
import com.example.kerberos.stater.configuration.KerberosObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jackie wang
 * @since 2021/3/2 11:22
 */
@RestController
@RequestMapping(value = "/hbase/kbs")
public class HBaseKbsController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HBaseService hBaseService;

    @GetMapping("tables")
    public String getAllTableNames() {
        List<String> result = hBaseService.getAllTableNames();
        log.info("Logback info level:{}",result.toString());
        log.warn("Logback warn level:{}",result.toString());
        log.error("Logback error level:{}",result.toString());
        return result.toString();
    }

}
