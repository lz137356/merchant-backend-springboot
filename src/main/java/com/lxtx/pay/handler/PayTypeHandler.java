package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.PayType;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

public class PayTypeHandler extends SimpleIbatisEntityHandler<PayType> {
    public int deleteById(int id){
        int delete = super.delete(id);
        return delete;
    }
}
