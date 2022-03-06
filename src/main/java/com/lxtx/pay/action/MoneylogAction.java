package com.lxtx.pay.action;

import com.lxtx.pay.handler.MoneylogHandler;

public class MoneylogAction extends  BaseAction{

    private MoneylogHandler moneylogHandler;


//    public void queryAllmoneylog(){
//        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
//        String type = this.servletRequest.getParameter("Type");
//        String scene = this.servletRequest.getParameter("SceneInfo");
//        String date = this.servletRequest.getParameter("Date");
//        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
//        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
//        int rowIndex = PageUtils.getRowIndex(page, limit);
//        Map params = new HashMap();
//        params.put("rowIndex",rowIndex);
//        params.put("limit",limit);
//        params.put("appId",cpInfo.getAppId());
//        if(type!=null && !type.isEmpty()){
//            params.put("Type",Integer.valueOf(type));
//        }
//        if(scene!=null && !scene.isEmpty()){
//            params.put("SceneInfo",Integer.valueOf(scene));
//        }
//        if(date!=null && !date.isEmpty()){
//            String[] range = date.split("~");
//            String date1=range[0].trim()+" 00:00:00";
//            String date2=range[1].trim()+" 24:00:00";
//            params.put("startTime",date1);
//            params.put("endTime",date2);
//        }
//
//        List<Moneylog> all = moneylogHandler.queryAllmoneylog(page,limit,params);
//        int total = moneylogHandler.queryAllmoneylogcount(params);
//        GsonBuilder gsonbuilder = new GsonBuilder();
//        gsonbuilder.serializeNulls();
//        Gson gson = gsonbuilder.create();
//        Map map=new HashMap();
//        map.put("code",0);
//        map.put("msg","");
//        map.put("count",total);
//        map.put("data",all);
//        String s1 = gson.toJson(map);
//        output(s1);
//
//
//    }





    public MoneylogHandler getMoneylogHandler() {
        return moneylogHandler;
    }

    public void setMoneylogHandler(MoneylogHandler moneylogHandler) {
        this.moneylogHandler = moneylogHandler;
    }
}
