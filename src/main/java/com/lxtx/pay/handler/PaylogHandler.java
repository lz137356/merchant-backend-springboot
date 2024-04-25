package com.lxtx.pay.handler;

import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.pojo.Paylog;
import com.lxtx.pay.vo.PayLogStaticticsVO;
import com.lxtx.pay.vo.PayLogVo;
import com.lxtx.pay.vo.PaylogExportVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.List;

public class PaylogHandler extends SimpleIbatisEntityHandler<Paylog> {

    public Long insertPayLog(Paylog paylog) {
        insert(paylog);
        return paylog.getId();
    }

    public int changeStatus(PayLogReqDTO reqDTO) {
        return update("changeStatus", reqDTO);
    }

    public int changeSyncCnt(PayLogReqDTO reqDTO) {
        return update("changeSyncCnt", reqDTO);
    }

    public int updatePayLogRastreo(Paylog payLog) {
        return update("updatePayLogRastreo", payLog);
    }

    public List<Paylog> queryPayLogByOrderId(String orderId) {
        return queryForList("queryPayLogByOrderId", orderId);
    }

    public List<PayLogVo> queryPayLogPageList(PayLogReqDTO reqDTO) {
        return queryForList("queryPayLogPageList", reqDTO);
    }

    public PayLogStaticticsVO queryPayLogPageListStatictics(PayLogReqDTO reqDTO) {
        return queryForObject("queryPayLogPageListStatictics", reqDTO);
    }

    public List<Paylog> selectAll() {
        return queryForList("selectAll");
    }

    public List<PaylogExportVO> exportExcelPayLogList(PayLogReqDTO reqDTO) {
        return queryForList("exportExcelPayLogList", reqDTO);
    }

    public List<PayLogVo> queryPayLogHistoryPageList(PayLogReqDTO reqDTO) {
        return queryForList("queryPayLogHistoryPageList", reqDTO);
    }

    public PayLogStaticticsVO queryPayLogHistoryPageListStatictics(PayLogReqDTO reqDTO) {
        return queryForObject("queryPayLogHistoryPageListStatictics", reqDTO);
    }

    public List<PaylogExportVO> exportExcelPayLogHistoryList(PayLogReqDTO reqDTO) {
        return queryForList("exportExcelPayLogHistoryList", reqDTO);
    }
}
