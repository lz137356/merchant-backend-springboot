package com.lxtx.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.PayLogMatchUtrReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.vo.PayLogStaticticsVO;
import com.lxtx.pay.vo.PayLogVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.text.ParseException;
import java.util.List;

public interface PayLogService {
    int changeStatus(Long id)  throws ParseException;

    int changeSyncCnt(PayLogReqDTO reqDTO);

    List<PayLogVo> queryPayLogPageList(PayLogReqDTO reqDTO);

    PayLogStaticticsVO queryPayLogPageListStatictics(PayLogReqDTO reqDTO);

    HSSFWorkbook exportExcelPayLogList(PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException;

    List<PayLogVo> queryPayLogHistoryPageList(PayLogReqDTO reqDTO);

    PayLogStaticticsVO queryPayLogHistoryPageListStatictics(PayLogReqDTO reqDTO);

    HSSFWorkbook exportExcelPayLogHistoryList(PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException;

}
