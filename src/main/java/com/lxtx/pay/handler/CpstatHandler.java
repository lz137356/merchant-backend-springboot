package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.Cpstat;
import com.lxtx.pay.utils.PageUtils;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CpstatHandler extends SimpleIbatisEntityHandler<Cpstat> {


    public List<Cpstat> querycpstat(int page,int limit,Map map){

        int rowIndex = PageUtils.getRowIndex(page, limit);
        map.put("rowIndex",rowIndex);
        map.put("limit",limit);
        return queryForList("queryAllByAppid",map);
    }
    public int querycpstatcount(Map map){

        return queryForObject("queryAllByAppidcount",map);
    }
    public  Cpstat queryByDate(Map map){
        List<Cpstat> queryByDate = queryForList("queryByDate", map);
        if ( queryByDate.size()>0&& queryByDate!=null){
            return queryByDate.get(0);
       }
        return null;
    }


}
