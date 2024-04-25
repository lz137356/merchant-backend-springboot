package com.lxtx.pay.service;

import com.lxtx.pay.dto.WithdrawLogReqDTO;
import com.lxtx.pay.vo.WithdrawLogStaticticsVO;
import com.lxtx.pay.vo.WithdrawLogVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public interface WithdrawLogService {

    List<WithdrawLogVO> queryWithdrawLogPageList(WithdrawLogReqDTO reqDTO);

    WithdrawLogStaticticsVO queryWithdrawLogPageListStatictics(WithdrawLogReqDTO reqDTO);

    HSSFWorkbook exportExcelWithdrawLogList(WithdrawLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException;

}
