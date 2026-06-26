package com.lxtx.pay.mapper;

import com.lxtx.pay.dto.RechargeReqDTO;
import com.lxtx.pay.pojo.Recharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RechargeMapper {
    List<Recharge> pageList(@Param("reqDTO") RechargeReqDTO reqDTO);
    int countTotal(@Param("reqDTO") RechargeReqDTO reqDTO);
}
