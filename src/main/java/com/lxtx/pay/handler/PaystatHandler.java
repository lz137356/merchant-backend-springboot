package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.Paystat;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PaystatHandler extends SimpleIbatisEntityHandler<Paystat> {

    public List<Paystat> querypaystat(Map map){

        return queryForList("queryAll",map);
    }
    public List<Paystat> querypaystatdates(Map map){

        return queryForList("querypaystatdates",map);
    }


}
