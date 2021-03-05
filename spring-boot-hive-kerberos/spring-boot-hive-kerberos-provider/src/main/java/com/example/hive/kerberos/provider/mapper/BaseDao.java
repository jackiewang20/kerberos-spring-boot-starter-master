package com.example.hive.kerberos.provider.mapper;

import com.example.hive.kerberos.provider.bean.UserInfoQUERY;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseDao<T> {

    Integer add(T t) throws Exception;

    Integer update(T t) throws Exception;

    Integer delete(Integer id) throws Exception;

    T getById(Integer id) throws Exception;

    /**
     * 默认top 10
     * @return
     * @throws Exception
     */
    List<T> getListTop10() throws Exception;

    /**
     * 分页查询
     * @param userInfoQUERY userInfoQUERY 包含user属性，和BaseDao分页属性；
     * @return
     * @throws Exception
     */
    List<T> getPageList(UserInfoQUERY userInfoQUERY) throws Exception;


}
