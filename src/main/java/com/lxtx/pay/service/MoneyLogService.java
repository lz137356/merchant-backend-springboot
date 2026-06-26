package com.lxtx.pay.service;

import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.vo.MoneyLogVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface MoneyLogService {

    List<MoneyLogVO> queryMoneyLogPageList(MoneyLogReqDTO reqDTO);

    int queryMoneyLogPageListCount(MoneyLogReqDTO reqDTO);

    HSSFWorkbook exportExcelMoneyLogList(MoneyLogReqDTO reqDTO)
            throws NoSuchFieldException, IllegalAccessException;

    void exportZipMoneyList(MoneyLogReqDTO reqDTO, HttpServletResponse response) throws IOException;
}
