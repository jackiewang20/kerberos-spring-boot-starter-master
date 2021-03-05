package com.example.hive.kerberos.provider.controller;

import com.alibaba.fastjson.JSON;
import com.example.common.basic.ResponseJson;
import com.example.hive.kerberos.provider.bean.UserInfoDO;
import com.example.hive.kerberos.provider.bean.UserInfoQUERY;
import com.example.hive.kerberos.provider.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hive/userinfo")
public class ApiUserInfoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据id查询userInfo。
     * TODO 增加hibernateValidate验证框架验证参数
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getbyid", method = RequestMethod.GET)
    public ResponseJson getById(Integer id) throws Exception {
        return userInfoService.getById(id);
    }

    @RequestMapping(value = "pagelist", method = RequestMethod.GET)
    public ResponseJson pageList(@RequestParam Integer pageNow,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String username) throws Exception {
        UserInfoQUERY userInfoQUERY = new UserInfoQUERY();
        userInfoQUERY.setPageNow(pageNow);
        userInfoQUERY.setPageSize(pageSize);
        userInfoQUERY.setUserInfoDO(new UserInfoDO(username));
        return userInfoService.getPageList(userInfoQUERY);
    }

    /**
     * 分页查询
     * @param json 调用参数示例：{"pageNow":1,"pageSize":2}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getpagelist",method = RequestMethod.GET)
    public ResponseJson getPageList(@RequestParam String json) throws Exception {
        UserInfoQUERY userInfoQUERY = JSON.parseObject(json, UserInfoQUERY.class);
        return userInfoService.getPageList(userInfoQUERY);
    }

}
