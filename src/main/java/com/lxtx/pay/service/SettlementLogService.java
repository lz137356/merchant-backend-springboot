package com.lxtx.pay.service;

import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.vo.SettlementLogStatisticsVO;
import com.lxtx.pay.vo.SettlementLogVO;

import java.util.List;

public interface SettlementLogService {

    int addOneSettlementLog(SettlementLogReqDTO reqDTO);

    List<SettlementLogVO> querySettlementLogPageList(SettlementLogReqDTO reqDTO);

    SettlementLogStatisticsVO querySettlementLogPageListStatistics(SettlementLogReqDTO reqDTO);
}
