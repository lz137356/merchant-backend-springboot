package com.lxtx.pay.service;

import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.vo.MoneyLogVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public interface MoneyLogService {

    List<MoneyLogVO> queryMoneyLogPageList(MoneyLogReqDTO reqDTO);

    int queryMoneyLogPageListCount(MoneyLogReqDTO reqDTO);

    HSSFWorkbook exportExcelMoneyList(MoneyLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException;
}
