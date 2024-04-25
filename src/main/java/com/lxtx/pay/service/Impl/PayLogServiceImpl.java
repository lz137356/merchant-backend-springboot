package com.lxtx.pay.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.PayLogMatchUtrReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.IcIcBankRecord;
import com.lxtx.pay.pojo.Paylog;
import com.lxtx.pay.rocketmq.RocketMqProducer;
import com.lxtx.pay.service.PayLogService;
import com.lxtx.pay.utils.ExportUtils;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.PayLogStaticticsVO;
import com.lxtx.pay.vo.PayLogVo;
import com.lxtx.pay.vo.PaylogExportVO;
import com.qlzf.commons.spring.SpringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class PayLogServiceImpl implements PayLogService {
    @InitBinder
    public void initDateFormate(WebDataBinder dataBinder) {
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @Autowired
    private PaylogHandler paylogHandler;


    @Autowired
    private CpInfoHandler cpInfoHandler;

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Override
    public List<PayLogVo> queryPayLogPageList(PayLogReqDTO reqDTO) {
        reqDTO.setRowIndex(PageUtils.getRowIndex(reqDTO.getPage(), reqDTO.getLimit()));

        if (reqDTO.getAmount() != null) {
            reqDTO.setAmount(reqDTO.getAmount() * 100L);
        }
        return paylogHandler.queryPayLogPageList(reqDTO);
    }

    @Override
    public int changeStatus(Long id) throws ParseException {
        PayLogReqDTO payLogReqDTO = new PayLogReqDTO();

        payLogReqDTO.setId(id);

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String format = yyyyMMdd.format(new Date());
        payLogReqDTO.setDescription("最后一次回调时间为" + format);

        String payDay = format.split(" ")[0];
        payLogReqDTO.setPayDay(payDay);

        Date date = yyyyMMdd.parse(payDay);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        Paylog p = paylogHandler.select(id);
        CpInfo c = cpInfoHandler.select(p.getAppId());

        if (c.getRemainDelayDay() >= 0) {
            //如果当前是周六日则顺延
            while (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                    gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            if (c.getRemainDelayDay() > 0) {
                for (int i = 0; i < c.getRemainDelayDay(); i++) {
                    gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    //如果周六日则顺延
                    while (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                            gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
            } else {
                gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
                while (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }

        date = gregorianCalendar.getTime();
        String formatDate = yyyyMMdd.format(date);

        Date today = new Date();

        if (date.after(today)) { //如果生效日在回调时间之后，则不改变结算日期
            payLogReqDTO.setValidDay(formatDate);
        } else { //
            payLogReqDTO.setValidDay(yyyyMMdd.format(today));
        }

        return paylogHandler.changeStatus(payLogReqDTO);
    }

    @Override
    public int changeSyncCnt(PayLogReqDTO reqDTO) {
        int i = paylogHandler.changeSyncCnt(reqDTO);
        if (i> 0) {
            push(reqDTO.getId() + "");
        }

        return i;
    }

    @Override
    public PayLogStaticticsVO queryPayLogPageListStatictics(PayLogReqDTO reqDTO) {
        if (reqDTO.getAmount() != null) {
            reqDTO.setAmount(reqDTO.getAmount() * 100L);
        }
        return paylogHandler.queryPayLogPageListStatictics(reqDTO);
    }

    @Override
    public HSSFWorkbook exportExcelPayLogList(PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException {

        List<PaylogExportVO> paylogExportVOS = paylogHandler.exportExcelPayLogList(reqDTO);

        for (PaylogExportVO p :
                paylogExportVOS) {
            p.setStatus("1".equals(p.getStatus()) ? "成功" : "待支付");
            p.setAmount(new BigDecimal(p.getAmount()).multiply(new BigDecimal("0.01")).toString());
            p.setPlatformFee(new BigDecimal(p.getPlatformFee()).multiply(new BigDecimal("0.01")).toString());
        }

        HSSFWorkbook sheets = ExportUtils.exportExcel(paylogExportVOS, PaylogExportVO.class);

        return sheets;
    }

    private void push(String message)  {
        try {
            String consumerStr = "666";

            Message msg = new Message("PushNotify", "push", message.getBytes());
            RocketMqProducer producer = (RocketMqProducer) SpringUtil.getBean("rocketMqProducer");
            producer.getDefaultMQProducer().send(msg);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PayLogVo> queryPayLogHistoryPageList(PayLogReqDTO reqDTO) {
        reqDTO.setRowIndex(PageUtils.getRowIndex(reqDTO.getPage(), reqDTO.getLimit()));

        if (reqDTO.getAmount() != null) {
            reqDTO.setAmount(reqDTO.getAmount() * 100L);
        }
        return paylogHandler.queryPayLogHistoryPageList(reqDTO);
    }

    @Override
    public PayLogStaticticsVO queryPayLogHistoryPageListStatictics(PayLogReqDTO reqDTO) {
        if (reqDTO.getAmount() != null) {
            reqDTO.setAmount(reqDTO.getAmount() * 100L);
        }
        return paylogHandler.queryPayLogHistoryPageListStatictics(reqDTO);
    }

    @Override
    public HSSFWorkbook exportExcelPayLogHistoryList(PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException {

        List<PaylogExportVO> paylogExportVOS = paylogHandler.exportExcelPayLogHistoryList(reqDTO);

        for (PaylogExportVO p :
                paylogExportVOS) {
            p.setStatus("1".equals(p.getStatus()) ? "成功" : "待支付");
            p.setAmount(new BigDecimal(p.getAmount()).multiply(new BigDecimal("0.01")).toString());
            p.setPlatformFee(new BigDecimal(p.getPlatformFee()).multiply(new BigDecimal("0.01")).toString());
        }

        HSSFWorkbook sheets = ExportUtils.exportExcel(paylogExportVOS, PaylogExportVO.class);

        return sheets;
    }
}
