package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.*;
import com.lxtx.pay.utils.PageUtils;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WithdrawOrderHandler extends SimpleIbatisEntityHandler<WithdrawOrder> {


    public  WithdrawOrder selectOne(WithdrawOrder w){
        return queryForObject("selectOne",w);
    }

    public int updateStatusAndSynccnt(Map map){
        return update("updateStatusAndSynccnt",map);
    }

    public void addOne(WithdrawOrder withdrawOrder){ insert(withdrawOrder);
    }






    public   int withdrawcount(Map map){
        return queryForObject("withdrawcount",map);
    }

    public List<WithdrawOrder> Allwithdraworder(int page,int limit,Map map){
        int rowIndex = PageUtils.getRowIndex(page, limit);
        map.put("rowIndex",rowIndex);
        map.put("limit",limit);
        return queryForList("Allwithdraworder",map);
    }
    public int Allwithdrawordercount(Map map){
        return queryForObject("Allwithdrawordercount",map);
    }
    public List<WithdrawOrder> selectWithdrawOrder(int appId){

        return queryForList("selectWithdrawOrder",appId);
    }
    public List<WithdrawOrder> queryAllByPageAndLimit(int page, int limit){
        int rowIndex = PageUtils.getRowIndex(page, limit);
        PageHelper pageHelper=new PageHelper(rowIndex,limit);
        return queryForList("queryAllByPageAndLimit",pageHelper);
    }
    public WithdrawOrder withdrawordertoday(Map paramMap){

        return queryForObject("withdrawordertoday",paramMap);
    }
    public WithdrawOrder withdraworderaweek(Map paramMap){

        return  queryForObject("withdraworderaweek",paramMap);
    }
    public WithdrawOrder withdraworderyesterday(Map paramMap){

        return queryForObject("withdraworderyesterday",paramMap);
    }
    public List<WithdrawOrder> querycondition(Map map){

        return queryForList("querycondition",map);
    }


    public CpstatOwn cpstatown(Map paramMap){
        return queryForObject("cpstatown",paramMap);
    }


    public List<WithdrawOrder> quarywithdraw(Long id){
        return queryForList("quarywithdraw",id);
    }
    public List<WithdrawOrder> querywithdraworderdates(Map map){
        return queryForList("querywithdraworderdates",map);
    }
}
