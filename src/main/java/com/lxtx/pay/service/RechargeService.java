package com.lxtx.pay.service;

import com.lxtx.pay.dto.RechargeAddReqDTO;
import com.lxtx.pay.dto.RechargeReqDTO;
import com.lxtx.pay.pojo.Recharge;
import com.lxtx.pay.vo.RechargeStatisticsVO;
import com.lxtx.pay.vo.RechargeVO;

import java.util.List;

public interface RechargeService {

    int addOneRecharge(RechargeAddReqDTO reqDTO);

    List<RechargeVO> queryRechargePageList(RechargeReqDTO reqDTO);

    RechargeStatisticsVO queryRechargePageListCount(RechargeReqDTO reqDTO);

}
