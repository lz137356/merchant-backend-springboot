package com.lxtx.pay.service.Impl;

import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.handler.MoneylogHandler;
import com.lxtx.pay.service.MoneyLogService;
import com.lxtx.pay.utils.ExportUtils;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.MoneyLogExportVO;
import com.lxtx.pay.vo.MoneyLogVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MoneyLogServiceImpl implements MoneyLogService {

    @Autowired
    private MoneylogHandler moneylogHandler;

    @Override
    public List<MoneyLogVO> queryMoneyLogPageList(MoneyLogReqDTO reqDTO) {

        if (StringUtils.isNotEmpty(reqDTO.getMoney())) {
            reqDTO.setMoney(new BigDecimal(reqDTO.getMoney()).multiply(new BigDecimal(100)).toString());
        }

        reqDTO.setRowIndex(PageUtils.getRowIndex(reqDTO.getPage(), reqDTO.getLimit()));

        List<MoneyLogVO> moneyLogVOList = moneylogHandler.queryMoneyLogPageList(reqDTO);
        for (MoneyLogVO m :
                moneyLogVOList) {
            Integer type = m.getType();
            Integer sceneInfo = m.getSceneInfo();
            try {
                if (org.apache.commons.lang3.StringUtils.isEmpty(m.getOrderId())) {
                    m.setOrderId(m.getNotes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (type == 1 && sceneInfo == 1) {
                m.setDetails("商户充值成功，余额增加。");
            } else if (type == 1 && sceneInfo == 3) {
                m.setDetails("商户提现失败，订单金额返回商户余额，余额增加。");
            } else if (type == 2 && sceneInfo == 3) {
                m.setDetails("商户进行提现，商户余额扣除订单金额，余额减少。");
            }
        }
        return moneyLogVOList;
    }

    @Override
    public int queryMoneyLogPageListCount(MoneyLogReqDTO reqDTO) {
        if (StringUtils.isNotEmpty(reqDTO.getMoney())) {
            reqDTO.setMoney(new BigDecimal(reqDTO.getMoney()).multiply(new BigDecimal(100)).toString());
        }
        return moneylogHandler.queryMoneyLogPageListCount(reqDTO);
    }

    @Override
    public HSSFWorkbook exportExcelMoneyList(MoneyLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException {
        List<MoneyLogExportVO> moneyLogExportVOS = moneylogHandler.exportExcelMoneyLogList(reqDTO);

        for (MoneyLogExportVO m :
                moneyLogExportVOS) {
            String type = m.getType();
            m.setType("1".equals(type) ? "增加" : "减少");

            String sceneInfo = m.getSceneInfo();
            m.setSceneInfo("1".equals(sceneInfo) ? "代收" : "2".equals(sceneInfo) ? "调账" : ("3".equals(sceneInfo) && "1".equals(type)) ? "代付退回" : ("3".equals(sceneInfo) && "2".equals(type)) ? "代付成功" : "其他");


//            m.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(m.getCreateTime()));
        }

        HSSFWorkbook sheets = ExportUtils.exportExcel(moneyLogExportVOS, MoneyLogExportVO.class);

        return sheets;
    }
}
