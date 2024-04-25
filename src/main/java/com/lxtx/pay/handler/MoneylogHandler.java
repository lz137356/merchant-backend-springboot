package com.lxtx.pay.handler;

import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.pojo.Moneylog;
import com.lxtx.pay.vo.MoneyLogExportVO;
import com.lxtx.pay.vo.MoneyLogVO;
import com.lxtx.pay.vo.PaylogExportVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.List;
import java.util.Map;

public class MoneylogHandler extends SimpleIbatisEntityHandler<Moneylog> {

    public List<MoneyLogVO> queryMoneyLogPageList(MoneyLogReqDTO reqDTO) {
        return queryForList("queryMoneyLogPageList", reqDTO);
    }

    public int queryMoneyLogPageListCount(MoneyLogReqDTO reqDTO) {
        return queryForObject("queryMoneyLogPageListCount", reqDTO);
    }

    public List<MoneyLogExportVO> exportExcelMoneyLogList(MoneyLogReqDTO reqDTO) {
        return queryForList("exportExcelMoneyLogList", reqDTO);
    }

}
