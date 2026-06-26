package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.CpBank;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CpBankHandler extends SimpleIbatisEntityHandler<CpBank> {

      public List<CpBank> querycpbank(Map map){

          return queryForList("querycpbank",map);
      }

    public int querycount(Map map){
        return queryForObject("querycount",map);
    }
    public int deleteById(Integer id){
        int delete = super.delete(id);
        return delete;
    }


    public List<CpBank> queryAll(Map map){

          return queryForList("queryAll",map);

    }

}
