package com.example.hive.kerberos.provider.mapper;

import com.example.hive.kerberos.provider.bean.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserInfoMapper extends BaseDao<UserInfoDO> {

    /**
     * 记录数
     * @return
     * @throws Exception
     */
    Integer getCount() throws Exception;

}
