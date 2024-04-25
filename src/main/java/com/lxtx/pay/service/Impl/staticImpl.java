package com.lxtx.pay.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.pojo.Paylog;
import com.lxtx.pay.service.StaticService;
import com.lxtx.pay.vo.StaticticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class staticImpl implements StaticService {

    @Autowired
    private PaylogHandler paylogHandler;

    @Autowired
    private CpInfoHandler cpInfoHandler;


    public JSONObject statictics() {

        List<Paylog> paylogList = paylogHandler.selectAll();

        Long sumAmount = 0L;
        Long payAmount = 0L;
        ArrayList<StaticticsVO> staticticsVOS = new ArrayList<>();


        for (Paylog p :
                paylogList) {
            StaticticsVO staticticsVO = new StaticticsVO();
            staticticsVO.setId(p.getId());
            staticticsVO.setAppid(p.getAppId().toString());

            String notifyData = p.getNotifyData();
            JSONObject jsonObject = JSON.parseObject(notifyData);
            String amount = jsonObject.getString("amount");
            Double amountDouble = (Double.parseDouble(amount) *100);
            sumAmount += amountDouble.longValue()/100L;

            Long amount1 = p.getAmount().longValue();
            payAmount += amount1 / 100l;


            staticticsVO.setOrderAmount(amount1);
            staticticsVO.setNotifyAmount(amountDouble.longValue());
            staticticsVO.setOrderId(p.getOrderId());
            staticticsVOS.add(staticticsVO);
        }

        System.out.println("回调总金额： " + sumAmount);
        System.out.println("订单总金额： " + payAmount);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sumAmount",sumAmount);
        jsonObject.put("payAmount",payAmount);
        jsonObject.put("staticticsVOS",staticticsVOS);


        return jsonObject;

    }

}
