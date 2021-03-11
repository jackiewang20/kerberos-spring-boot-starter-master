package com.example.hive.kerberos.provider.service.impl;

import com.example.common.basic.EnumCode;
import com.example.common.basic.ResponseJson;
import com.example.hive.kerberos.provider.bean.UserInfoDO;
import com.example.hive.kerberos.provider.bean.UserInfoQUERY;
import com.example.hive.kerberos.provider.mapper.UserInfoMapper;
import com.example.hive.kerberos.provider.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseJson add(UserInfoDO userInfoDO) throws Exception {
        return null;
    }

    @Override
    public ResponseJson update(UserInfoDO userInfoDO) throws Exception {
        return null;
    }

    @Override
    public ResponseJson delete(Integer id) throws Exception {
        return null;
    }

    @Override
    public ResponseJson getById(Integer id) throws Exception {
//        ResponseJson result = new ResponseJson();
//        result.setData(userInfoMapper.getById(id));
//        return result;
        return new ResponseJson(userInfoMapper.getById(id));
    }

    @Override
    public ResponseJson getListTop10() throws Exception {
        return new ResponseJson(userInfoMapper.getListTop10());
    }

    /**
     * 根据查询条件分页查询
     * @param userInfoQUERY userInfoQUERY 包含user属性，和BaseDao分页属性；
     * @return
     * @throws Exception
     */
    @Override
    public ResponseJson getPageList(UserInfoQUERY userInfoQUERY) throws Exception {
        ResponseJson result = new ResponseJson();
        // 参数校验
//        if(userInfoQUERY==null || userInfoQUERY.getUserInfoDO()==null) {
//            result.setErrorCode(ResponseJson.EnumCode.CODE_PARAMETER_INVALID.getCode());
//            result.setErrorMsg(ResponseJson.EnumCode.CODE_PARAMETER_INVALID.getText());
//            return result;
//        }
        if("".equals(userInfoQUERY.getPageNow())){
            result.setErrorCode(EnumCode.CODE_PARAMETER_MISSING.getCode());
            result.setErrorMsg(EnumCode.CODE_PARAMETER_MISSING.getText()+" :pageNo");
            return result;
        }

        if("".equals(userInfoQUERY.getPageSize())){
            result.setErrorCode(EnumCode.CODE_PARAMETER_MISSING.getCode());
            result.setErrorMsg(EnumCode.CODE_PARAMETER_MISSING.getText()+" :pageSize");
            return result;
        }

        List<UserInfoDO> pageList = userInfoMapper.getPageList(userInfoQUERY);
        result.setData(pageList);
        if(logger.isInfoEnabled()) {
            logger.info("【UserInfoImplServiceImpl#getPageList接口】params:{},return:{}","getPageList(UserQUERY userQUERY)", userInfoQUERY,result);
        }
        return result;
    }
}
