package com.lxtx.pay.handler;

import com.lxtx.pay.dto.WithdrawLogReqDTO;
import com.lxtx.pay.pojo.PageHelper;
import com.lxtx.pay.pojo.Withdrawlog;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.WithdrawLogExportVO;
import com.lxtx.pay.vo.WithdrawLogStaticticsVO;
import com.lxtx.pay.vo.WithdrawLogVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.List;
import java.util.Map;

public class WithdrawlogHandler extends SimpleIbatisEntityHandler<Withdrawlog> {

   /* public String querywithdeawlog(Withdrawlog withdrawlog ) {
            return select(withdrawlog);
    }*/


    public   int withdrawcount(Map map){
        return queryForObject("withdrawcount",map);
    }

    public List<Withdrawlog> Allwithdrawlog(int page,int limit,Map map){
        int rowIndex = PageUtils.getRowIndex(page, limit);
        map.put("rowIndex",rowIndex);
        map.put("limit",limit);
        return queryForList("Allwithdrawlog",map);


    }
    public int Allwithdrawlogcount(Map map){

        return queryForObject("Allwithdrawlogcount",map);


    }
    public List<Withdrawlog> queryAllByPageAndLimit(int page, int limit){
        int rowIndex = PageUtils.getRowIndex(page, limit);
        PageHelper pageHelper=new PageHelper(rowIndex,limit);
        return queryForList("queryAllByPageAndLimit",pageHelper);


    }
    public Withdrawlog withdrawlogtoday(Map paramMap){

        return queryForObject("withdrawlogtoday",paramMap);
    }
    public Withdrawlog withdrawlogaweek(Map paramMap){

        return  queryForObject("withdrawlogaweek",paramMap);
    }
    public Withdrawlog withdrawlogyesterday(Map paramMap){

        return queryForObject("withdrawlogyesterday",paramMap);
    }
    public List<Withdrawlog> querycondition(Map map){

        return queryForList("querycondition",map);
    }
    public List<Withdrawlog> quarywithdraw(Long id){
        return queryForList("quarywithdraw",id);
    }
    public List<Withdrawlog> querywithdrawlogdates(Map map){
        return queryForList("querywithdrawlogdates",map);
    }

    public List<WithdrawLogVO> queryWithdrawLogPageList(WithdrawLogReqDTO reqDTO) {
        return queryForList("queryWithdrawLogPageList", reqDTO);
    }

    public WithdrawLogStaticticsVO queryWithdrawLogStatictics(WithdrawLogReqDTO reqDTO) {
        return queryForObject("queryWithdrawLogStatictics", reqDTO);
    }

    public List<WithdrawLogExportVO> exportExcelWithdrawLogList(WithdrawLogReqDTO reqDTO) {
        return queryForList("exportExcelWithdrawLogList", reqDTO);
    }
}
