package com.lxtx.pay.service.Impl;

import com.lxtx.pay.dto.CpInfoFrozenRemainReqDTO;
import com.lxtx.pay.dto.SettlementLogReqDTO;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.SettlementLogHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.SettlementLog;
import com.lxtx.pay.service.SettlementLogService;
import com.lxtx.pay.utils.CommonUtil;
import com.lxtx.pay.vo.SettlementLogStatisticsVO;
import com.lxtx.pay.vo.SettlementLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SettlementLogServiceImpl implements SettlementLogService {

    @Autowired
    private SettlementLogHandler settlementLogHandler;

    @Autowired
    private CpInfoHandler cpInfoHandler;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addOneSettlementLog(SettlementLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");

        String faitAmount = reqDTO.getFaitAmount();
        long faitAmountLong = new BigDecimal(faitAmount).multiply(new BigDecimal(100)).longValue();

        CpInfoFrozenRemainReqDTO cpInfoFrozenRemainReqDTO = new CpInfoFrozenRemainReqDTO();
        cpInfoFrozenRemainReqDTO.setAppId(cpInfo.getAppId() + "");
        cpInfoFrozenRemainReqDTO.setAmount(faitAmountLong);
        int i = cpInfoHandler.frozenCpInfoRemain(cpInfoFrozenRemainReqDTO);
        if (i == 0) {
            throw new RuntimeException();
        }

        SettlementLog settlementLog = new SettlementLog();
        settlementLog.setStatus(0);
        settlementLog.setCreateTime(CommonUtil.getDate(new Date()));
        settlementLog.setAppId(cpInfo.getAppId() + "");

        settlementLog.setProduct(reqDTO.getProduct());
        settlementLog.setCard(reqDTO.getCard());
        settlementLog.setAccountType(Integer.parseInt(reqDTO.getAccountType()));
        settlementLog.setBankName(reqDTO.getBankName());
        settlementLog.setBankNetName(reqDTO.getBankNetName());
        settlementLog.setAccountName(reqDTO.getAccountName());
        settlementLog.setAccount(reqDTO.getAccount());
        settlementLog.setFaitAmount(faitAmountLong+"");

        Long id = settlementLogHandler.insertSettlementLog(settlementLog);
        if (id == null || id == 0) {
            throw new RuntimeException();
        }
        return 1;
    }

    @Override
    public List<SettlementLogVO> querySettlementLogPageList(SettlementLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId() + "");
        List<SettlementLogVO> settlementLogVOS = settlementLogHandler.querySettlementLogPageList(reqDTO);
        return settlementLogVOS;
    }

    @Override
    public SettlementLogStatisticsVO querySettlementLogPageListStatistics(SettlementLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId() + "");

        return settlementLogHandler.querySettlementLogPageListStatistics(reqDTO);
    }
}
