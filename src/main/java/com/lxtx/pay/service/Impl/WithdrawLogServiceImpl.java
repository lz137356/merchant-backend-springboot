package com.lxtx.pay.service.Impl;

import com.lxtx.pay.dto.WithdrawLogReqDTO;
import com.lxtx.pay.handler.WithdrawlogHandler;
import com.lxtx.pay.service.WithdrawLogService;
import com.lxtx.pay.utils.ExportUtils;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.WithdrawLogExportVO;
import com.lxtx.pay.vo.WithdrawLogStaticticsVO;
import com.lxtx.pay.vo.WithdrawLogVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WithdrawLogServiceImpl implements WithdrawLogService {

    @Autowired
    private WithdrawlogHandler withdrawlogHandler;

    @Override
    public List<WithdrawLogVO> queryWithdrawLogPageList(WithdrawLogReqDTO reqDTO) {
        reqDTO.setRowIndex(PageUtils.getRowIndex(reqDTO.getPage(), reqDTO.getLimit()));
        return withdrawlogHandler.queryWithdrawLogPageList(reqDTO);
    }

    @Override
    public WithdrawLogStaticticsVO queryWithdrawLogPageListStatictics(WithdrawLogReqDTO reqDTO) {
        return withdrawlogHandler.queryWithdrawLogStatictics(reqDTO);
    }

    @Override
    public HSSFWorkbook exportExcelWithdrawLogList(WithdrawLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException {
        List<WithdrawLogExportVO> withdrawlogExportVOS = withdrawlogHandler.exportExcelWithdrawLogList(reqDTO);

        for (WithdrawLogExportVO w :
                withdrawlogExportVOS) {
            w.setStatus("2".equals(w.getStatus()) ? "成功" : "-2".equals(w.getStatus()) ? "失败" : "处理中");
            w.setAmount(new BigDecimal(w.getAmount()).multiply(new BigDecimal("0.01")).toString());
            w.setPlatformFee(new BigDecimal(w.getPlatformFee()).multiply(new BigDecimal("0.01")).toString());
        }
        HSSFWorkbook sheets = ExportUtils.exportExcel(withdrawlogExportVOS, WithdrawLogExportVO.class);

        return sheets;
    }
}
