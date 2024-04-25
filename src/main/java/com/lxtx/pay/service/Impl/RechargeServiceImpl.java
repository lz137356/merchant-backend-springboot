package com.lxtx.pay.service.Impl;

import com.lxtx.pay.dto.RechargeAddReqDTO;
import com.lxtx.pay.dto.RechargeReqDTO;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.handler.RechargeHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Paylog;
import com.lxtx.pay.pojo.Recharge;
import com.lxtx.pay.service.RechargeService;
import com.lxtx.pay.utils.CommonUtil;
import com.lxtx.pay.vo.RechargeStatisticsVO;
import com.lxtx.pay.vo.RechargeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private PaylogHandler paylogHandler;

    @Autowired
    private RechargeHandler rechargeHandler;

    @Autowired
    private CpInfoHandler cpInfoHandler;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addOneRecharge(RechargeAddReqDTO reqDTO) {

        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");

        String faitAmount = reqDTO.getFaitAmount();
        long amount = new BigDecimal(faitAmount).multiply(new BigDecimal(100)).longValue();

        Paylog paylog = new Paylog();
        paylog.setAmount(amount);
        paylog.setPlatformFee(0);
        paylog.setStatus(0);
        paylog.setStatus1(0);
        paylog.setAppId(cpInfo.getAppId());
        paylog.setType(1);
        paylog.setOrderId(cpInfo.getAppId() + "_recharge_" + System.currentTimeMillis() / 1000);

        Date now = CommonUtil.getDate(new Date());
        paylog.setCreateTime(now);
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(now);
        paylog.setCreateDay(Integer.parseInt(yyyyMMdd));
        paylog.setPayDay(0);


//        Long payLogId = paylogHandler.insertPayLog(paylog);
//        if (payLogId == 0L) {
//            throw new RuntimeException();
//        }

        Recharge recharge = new Recharge();
        recharge.setAppId(cpInfo.getAppId());
        recharge.setComment("");
        recharge.setCreateTime(now);
        recharge.setFaitAmount(amount + "");
//        recharge.setPayLogId(payLogId);
        Long rechargeId = rechargeHandler.insertRecharge(recharge);
        if (rechargeId == 0) {
            throw new RuntimeException();
        }
        return 1;
    }

    @Override
    public List<RechargeVO> queryRechargePageList(RechargeReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId());

        return rechargeHandler.queryRechargePageList(reqDTO);
    }

    @Override
    public RechargeStatisticsVO queryRechargePageListCount(RechargeReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId());

        return rechargeHandler.queryRechargePageListStatistics(reqDTO);
    }
}
