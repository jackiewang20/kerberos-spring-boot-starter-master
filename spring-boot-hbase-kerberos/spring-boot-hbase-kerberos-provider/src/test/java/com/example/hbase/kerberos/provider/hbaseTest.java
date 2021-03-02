package com.example.hbase.kerberos.provider;

import com.example.hbase.stater.component.HBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author jackie wang
 * @since 2021/3/1 16:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class hbaseTest {
    @Autowired
    private HBaseService hBaseService;

    /**
     * 获取所有表名
     */
    @Test
    public void testGetAllTableNames() {
        List<String> result = hBaseService.getAllTableNames();
        result.forEach(System.out::println);
    }

    @Test
    public void test1(){

    }

}