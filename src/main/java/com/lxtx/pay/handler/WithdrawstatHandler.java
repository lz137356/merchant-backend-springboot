package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.Withdrawlog;
import com.lxtx.pay.pojo.Withdrawstat;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WithdrawstatHandler extends SimpleIbatisEntityHandler<Withdrawstat> {


    public List<Withdrawstat> querywithdrawstat(Map map){

        return queryForList("querywithdrawstat",map);
    }
    public List<Withdrawstat> querywithdrawstatdates(Map map){

        return queryForList("querywithdrawstatdates",map);
    }

}
