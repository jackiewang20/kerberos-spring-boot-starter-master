package com.example.hive.kerberos.provider.service;

import com.example.common.basic.ResponseJson;
import com.example.hive.kerberos.provider.bean.UserInfoDO;
import com.example.hive.kerberos.provider.bean.UserInfoQUERY;

public interface UserInfoService {

    ResponseJson add(UserInfoDO userInfoDO) throws Exception;

    ResponseJson update(UserInfoDO userInfoDO) throws Exception;

    ResponseJson delete(Integer id) throws Exception;

    ResponseJson getById(Integer id) throws Exception;

    /**
     * 默认top 10
     * @return
     * @throws Exception
     */
    ResponseJson getListTop10() throws Exception;

    /**
     * 分页查询
     * @param userInfoQUERY userInfoQUERY 包含user属性，和BaseDao分页属性；
     * @return
     * @throws Exception
     */
    ResponseJson getPageList(UserInfoQUERY userInfoQUERY) throws Exception;



}
