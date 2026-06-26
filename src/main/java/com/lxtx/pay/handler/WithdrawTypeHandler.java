package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.WithdrawType;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

@Component
public class WithdrawTypeHandler extends SimpleIbatisEntityHandler<WithdrawType> {
    public int deleteById(int id){
        int delete = super.delete(id);
        return delete;
    }

}
