package com.lxtx.pay.handler;

import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.pojo.SettlementLog;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.SettlementLogStatisticsVO;
import com.lxtx.pay.vo.SettlementLogVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.List;

public class SettlementLogHandler extends SimpleIbatisEntityHandler<SettlementLog> {

    public Long insertSettlementLog(SettlementLog settlementLog){
        insert(settlementLog);
        return settlementLog.getId();
    }

    public List<SettlementLogVO> querySettlementLogPageList(SettlementLogReqDTO reqDTO) {
        Integer page = reqDTO.getPage();
        Integer limit = reqDTO.getLimit();
        reqDTO.setRowIndex(PageUtils.getRowIndex(page,limit));

        return queryForList("querySettlementLogPageList", reqDTO);
    }

    public int getAccountCounter(String string) {
        return queryForObject("getAccountCounter", string);
    }

    public SettlementLogStatisticsVO querySettlementLogPageListStatistics(SettlementLogReqDTO reqDTO) {
        return queryForObject("querySettlementLogPageListStatistics", reqDTO);
    }

}
