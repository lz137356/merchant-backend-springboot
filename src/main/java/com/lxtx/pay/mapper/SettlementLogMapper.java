package com.lxtx.pay.mapper;

import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.pojo.SettlementLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettlementLogMapper {
    List<SettlementLog> pageList(@Param("reqDTO") SettlementLogReqDTO reqDTO);
    int countTotal(@Param("reqDTO") SettlementLogReqDTO reqDTO);
}
