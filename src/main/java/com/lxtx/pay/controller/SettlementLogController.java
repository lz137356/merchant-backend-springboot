package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.SettlementLogService;
import com.lxtx.pay.vo.SettlementLogStatisticsVO;
import com.lxtx.pay.vo.SettlementLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay/settlementLog")
public class SettlementLogController {

    @Autowired
    private SettlementLogService settlementLogService;

    @RequestMapping("/settlement")
    public JSONObject settlement(SettlementLogReqDTO reqDTO){
        int i = settlementLogService.addOneSettlementLog(reqDTO);

        if (i>0){
            return Result.success("已提交",null);
        }
        return Result.fail("提交结算失败。");
    }

    @RequestMapping("/pageList")
    public JSONObject pageList(SettlementLogReqDTO settlementLogReqDTO){

        List<SettlementLogVO> settlementLogVOS = settlementLogService.querySettlementLogPageList(settlementLogReqDTO);
        SettlementLogStatisticsVO settlementLogStatisticsVO = settlementLogService.querySettlementLogPageListStatistics(settlementLogReqDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",settlementLogVOS);
        jsonObject.put("statistics",settlementLogStatisticsVO);

        return Result.success(jsonObject);
    }
}
