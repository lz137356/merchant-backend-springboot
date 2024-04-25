package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.IcIcBankRecord;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

public class IcIcBankRecordHandler extends SimpleIbatisEntityHandler<IcIcBankRecord> {

    public int matchUtrToSuccess(IcIcBankRecord icIcBankRecord){
        return update("matchUtrToSuccess",icIcBankRecord);
    };


    public IcIcBankRecord queryIcIcBankRecordByUtr(String utr){
        return queryForObject("queryIcIcBankRecordByUtr",utr);
    };
}
