package com.example.hive.kerberos.provider.bean;

import com.example.common.bean.BaseBean;

public class UserInfoQUERY extends BaseBean {
    private com.example.hive.kerberos.provider.bean.UserInfoDO userInfoDO;

    public com.example.hive.kerberos.provider.bean.UserInfoDO getUserInfoDO() {
        return userInfoDO;
    }

    public void setUserInfoDO(com.example.hive.kerberos.provider.bean.UserInfoDO userInfoDO) {
        this.userInfoDO = userInfoDO;
    }
}
