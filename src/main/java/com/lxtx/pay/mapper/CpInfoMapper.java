package com.lxtx.pay.mapper;

import com.lxtx.pay.dto.CpinfoReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CpInfoMapper {
    CpInfo cpinfoIsExist(CpinfoReqDTO reqDTO);
    
    CpInfo select(@Param("appId") Integer appId);
    
    int setSessionId(CpInfo cpInfo);
    
    int updateCpInfoPassword(@Param("reqDTO") Object reqDTO);
    
    int insertGoogleSecret(@Param("cpInfo") CpInfo cpInfo);
    
    CpInfo selectByUserName(@Param("username") String username);
}
