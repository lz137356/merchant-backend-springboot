package com.lxtx.pay.handler;

import com.lxtx.pay.dto.RechargeReqDTO;
import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.pojo.Recharge;
import com.lxtx.pay.pojo.SettlementLog;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.RechargeStatisticsVO;
import com.lxtx.pay.vo.RechargeVO;
import com.lxtx.pay.vo.SettlementLogStatisticsVO;
import com.lxtx.pay.vo.SettlementLogVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.List;

public class RechargeHandler extends SimpleIbatisEntityHandler<Recharge> {

    public Long insertRecharge(Recharge recharge){
        insert(recharge);
        return recharge.getId();
    }

    public List<RechargeVO> queryRechargePageList(RechargeReqDTO reqDTO) {
        Integer page = reqDTO.getPage();
        Integer limit = reqDTO.getLimit();
        reqDTO.setRowIndex(PageUtils.getRowIndex(page,limit));

        return queryForList("queryRechargePageList", reqDTO);
    }

    public RechargeStatisticsVO queryRechargePageListStatistics(RechargeReqDTO reqDTO) {
        return queryForObject("queryRechargePageListStatistics", reqDTO);
    }

}
