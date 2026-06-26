package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.Cpstat;
import com.lxtx.pay.pojo.CpstatOwn;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CpstatOwnHandler extends SimpleIbatisEntityHandler<CpstatOwn> {


    public CpstatOwn queryByDates(Map paramap){

        return queryForObject("queryByDates",paramap);

    }
    public CpstatOwn cpstatown(Map paramap){

        return queryForObject("cpstatown",paramap);

    }
    public CpstatOwn selectOne(Map paramap){

        return queryForObject("selectOne",paramap);

    }

}
