package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.*;
import com.lxtx.pay.pojo.*;
import com.lxtx.pay.utils.IPUtil;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.utils.RandomUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WithdrawOrderAction extends BaseAction {

    private CpInfoHandler cpInfoHandler;
    private WithdrawTypeHandler withdrawTypeHandler;
    private WithdrawOrderHandler withdraworderHandler;
    private CpstatOwnHandler cpstatownHandler;

    private MoneylogHandler moneylogHandler;
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public void addOne() {

        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        CpInfo cp = cpInfoHandler.select(cpInfo.getAppId());
        WithdrawOrder w = new WithdrawOrder();
        int appId = cpInfo.getAppId();
        String amount = this.servletRequest.getParameter("Amount");
        int amo = 0;
        if (!"".equals(amount) && amount != null) {
            Number v = Float.parseFloat(amount) * 100;
            amo = v.intValue();
            w.setAmount(amo);
            w.setAppId(appId);
            List<WithdrawOrder> list;
            String orderid;
            Map conmap = new HashMap();
            do {
                orderid = RandomUtils.getRandom();
                System.out.println("随机订单号为：" + orderid);
                conmap.put("OrderId", orderid);
                list = withdraworderHandler.Allwithdraworder(0, 1, conmap);
            } while (!list.isEmpty());
            w.setOrderId(orderid);
            String type = this.servletRequest.getParameter("Type");
            if (!"".equals(type) && type != null) {
                int ty = Integer.parseInt(type);
                w.setType(ty);
            }
            String BankCardNo = this.servletRequest.getParameter("BankCardNo");
            w.setBankCardNo(BankCardNo);
            //银行编号
            w.setBankCode("");
            //银行账户名
            String BankAccountName = this.servletRequest.getParameter("BankCardName");
            w.setBankAccountName(BankAccountName);
            //支行编码
            String BranchCode = this.servletRequest.getParameter("BranchCode");
            w.setBranchCode(BranchCode);
            w.setEmail("");
            w.setMobile(cpInfo.getPhoneNo());
            w.setStatus(0);
            w.setCreateTime(new Date());
            w.setConfirmStatus(0);
            w.setWithdrawTime(null);
            w.setTransactionNum("");
            w.setConfirmTime(null);
            //审核人id
            w.setConfirmUserId(0);

            BigDecimal withdrawFeeRate = cp.getWithdrawFeeRate();
            System.out.println("商户费率:::" + withdrawFeeRate);
            BigDecimal amobigDecimal = BigDecimal.valueOf(amo);
            System.out.println("提现金额:::" + amobigDecimal);
            BigDecimal multiply = withdrawFeeRate.multiply(amobigDecimal);
            System.out.println("平台手续费:::" + multiply);
            Integer withdrawFeeFix = cp.getWithdrawFeeFix();
            BigDecimal withdrawFeeFixDem = new BigDecimal(withdrawFeeFix);

            //平台总手续费
            BigDecimal add = multiply.add(withdrawFeeFixDem);
            w.setPlatformFee(add.intValue());
            //提现渠道
            w.setWithdrawTypeId(0);
            w.setThirdFee(0);

            //BigDecimal wdtotalfee = multiply.subtract(totalfee);
            System.out.println("支付过程平台收取总手续费" + add);
            Date date = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
            String format = s.format(date);
            w.setCreateDay(Integer.parseInt(format));
            w.setConfirmDay(0);
            w.setWithdrawDay(0);
            w.setNotifyData(null);
            w.setIp(IPUtil.getIpAdrress(this.servletRequest));
            System.out.println("客户端ip：" + IPUtil.getIpAdrress(this.servletRequest));
            w.setSyncCnt(3);
            w.setNextSyncTime(null);
            w.setNotifyUrl("");
            //货币类型
            w.setCurrency("");
            Date date1 = new Date();
            Moneylog moneylog = new Moneylog();
            moneylog.setType(2);
            moneylog.setSceneInfo(3);

            CpInfo select = cpInfoHandler.select(appId);

            long remain = select.getRemain();
            moneylog.setFrontMoney((int) remain);
            moneylog.setMoney(amo);
            moneylog.setQueenMoney((int) (remain - amo));
            moneylog.setDetails("订单创建");
            moneylog.setCreateTime(date1);
            moneylog.setAppId(cpInfo.getAppId());
            moneylog.setNotes("");

            Moneylog moneylog2 = new Moneylog();
            moneylog2.setType(2);
            moneylog2.setSceneInfo(3);

            CpInfo select2 = cpInfoHandler.select(appId);

            long remain2 = select.getRemain();
            moneylog2.setFrontMoney((int) (remain - amo));
            moneylog2.setMoney(add.intValue());
            moneylog2.setQueenMoney((int) (remain - amo - add.intValue()));
            moneylog2.setDetails("扣除手续费");
            moneylog2.setCreateTime(date1);
            moneylog2.setAppId(cpInfo.getAppId());
            moneylog2.setNotes("");

            Map amomap = new HashMap();

            //全部提现增加量=提现金额-手续费
            amomap.put("appid", cpInfo.getAppId());
            amomap.put("amount", amobigDecimal);
            amomap.put("fee", add);
            amomap.put("remain", amobigDecimal.add(add));

            int c = cpInfoHandler.updateAmo(amomap);
            if (c > 0) {
                withdraworderHandler.insert(w);
                moneylogHandler.insert(moneylog);
                moneylogHandler.insert(moneylog2);
                output("1");
            } else {
                output("0");
            }
        }
    }

    public void updateStatusAndSynccnt() {
        Map conmap = new HashMap();
        String id = this.servletRequest.getParameter("Id");
        WithdrawOrder w = new WithdrawOrder();
        if (!"".equals(id) || id != null) {
            conmap.put("id", id);
            w.setId(Integer.parseInt(id));
        }
        WithdrawOrder select = withdraworderHandler.selectOne(w);
        Integer syncCnt = select.getSyncCnt();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        //  map.put("count",count);
        if (syncCnt > 0) {
            conmap.put("status", 0);
            conmap.put("synccnt", syncCnt - 1);

            WithdrawOrder withdrawOrder = withdraworderHandler.selectOne(w);
            Integer thirdFee = withdrawOrder.getThirdFee();
            Integer platformFee = withdrawOrder.getPlatformFee();
            int t = platformFee;
            Integer appId = withdrawOrder.getAppId();
            CpInfo cpInfo = cpInfoHandler.select(appId);
            BigDecimal totalFee = BigDecimal.valueOf(t);


            Integer amount = withdrawOrder.getAmount();
            BigDecimal amoDecimal = BigDecimal.valueOf(amount);
            //变动金额
            BigDecimal add = amoDecimal.add(totalFee);


            Map conmap1 = new HashMap();
            conmap1.put("amount", amoDecimal);
            conmap1.put("fee", totalFee);
            conmap1.put("remain", add);
            conmap1.put("appid", appId);

            int i1 = cpInfoHandler.updateAmo(conmap1);
            if (i1 > 0) {
                int i = withdraworderHandler.updateStatusAndSynccnt(conmap);
                Moneylog moneylog = new Moneylog();
                moneylog.setType(2);
                moneylog.setSceneInfo(3);
                moneylog.setFrontMoney(cpInfo.getRemain().intValue());
                moneylog.setMoney(add.intValue());
                moneylog.setQueenMoney((int) (cpInfo.getRemain() - add.intValue()));
                moneylog.setDetails("订单驳回后再提交");
                moneylog.setCreateTime(new Date());
                moneylog.setAppId(cpInfo.getAppId());
                moneylog.setNotes("");

                moneylogHandler.insert(moneylog);
            }


        } else {


        }

        output(gson.toJson(map));


    }


    //今天的
    public void withdrawordertoday() {
//        Date todaydate=new Date();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
//        String today = simpleDateFormat.format(todaydate);
//        Map paramMap=new HashMap();
//        paramMap.put("statday",today);
//        CpstatOwn cpstatOwn = cpstatownHandler.queryByDates(paramMap);
//        GsonBuilder gsonbuilder = new GsonBuilder();
//        gsonbuilder.serializeNulls();
//        Gson gson = gsonbuilder.create();
//        Map map=new HashMap();
//        map.put("code",0);
//        map.put("msg","");
//        map.put("count",1);
//        map.put("data",cpstatOwn);
//        String s1 = gson.toJson(map);
//        output(s1);

        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap = new HashMap();
        if (cpInfo != null) {
            paramMap.put("AppId", cpInfo.getAppId());
        }
        WithdrawOrder withdrawordertoday = withdraworderHandler.withdrawordertoday(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", withdrawordertoday);
        map.put("data", withdrawordertoday);
        String s1 = gson.toJson(map);
        output(s1);


    }

    //一周的
    public void withdraworderaweek() {
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap = new HashMap();
        if (cpInfo != null) {
            paramMap.put("appId", cpInfo.getAppId());
        }
        WithdrawOrder w = withdraworderHandler.withdraworderaweek(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", w);
        map.put("data", w);
        String s1 = gson.toJson(map);
        System.out.println("json:::::" + s1);
        output(s1);

    }

    //昨天的
    public void withdraworderyesterday() {
//        Date todaydate=new Date();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
//        Date d2 = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
//        Map paramMap=new HashMap();
//        paramMap.put("statday",simpleDateFormat.format(d2));
//        CpstatOwn cpstatOwn = cpstatownHandler.queryByDates(paramMap);
//        GsonBuilder gsonbuilder = new GsonBuilder();
//        gsonbuilder.serializeNulls();
//        Gson gson = gsonbuilder.create();
//        Map map=new HashMap();
//        map.put("code",0);
//        map.put("msg","");
//        map.put("count",1);
//        map.put("data",cpstatOwn);
//        String s1 = gson.toJson(map);
//        output(s1);
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap = new HashMap();
        if (cpInfo != null) {
            paramMap.put("AppId", cpInfo.getAppId());
        }
        WithdrawOrder ll = withdraworderHandler.withdraworderyesterday(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", 1);
        map.put("data", ll);
        String s1 = gson.toJson(map);
        output(s1);


    }

    public void queryAll() {
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        int appId = cpInfo.getAppId();
        List<WithdrawOrder> all = withdraworderHandler.selectWithdrawOrder(appId);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", all.size());
        map.put("data", all);
        String s1 = gson.toJson(map);
        output(s1);

    }

    public void quarywithdraw() {
        String id = servletRequest.getParameter("id");
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<WithdrawOrder> all = withdraworderHandler.quarywithdraw(Long.parseLong(id));
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", all.size());
        map.put("data", all);
        String s1 = gson.toJson(map);
        output(s1);
    }

    public void Allwithdraworder() {
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        String id = this.servletRequest.getParameter("Id");
        String OrderId = this.servletRequest.getParameter("OrderId");
        String Type = this.servletRequest.getParameter("Type");
        String ConfirmStatus = this.servletRequest.getParameter("ConfirmStatus");
        String date = this.servletRequest.getParameter("date");
        String typeid = this.servletRequest.getParameter("typeid");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));

        Map paramMap = new HashMap();
        if (id != null && !"".equals(id)) {
            paramMap.put("Id", id);
        }
        if (OrderId != null && !"".equals(OrderId)) {
            paramMap.put("OrderId", OrderId);
        }
        if (Type != null && !"".equals(Type)) {
            paramMap.put("Type", Type);
        }
        if (typeid != null && !"".equals(typeid)) {
            paramMap.put("typeid", typeid);
        }
        if (ConfirmStatus != null && !"".equals(ConfirmStatus)) {
            paramMap.put("ConfirmStatus", ConfirmStatus);
        }
        if (date != null && !date.isEmpty()) {
            String[] range = date.split("~");
            String date1 = range[0].trim() + " 00:00:00";
            String date2 = range[1].trim() + " 24:00:00";
            paramMap.put("startTime", date1);
            paramMap.put("endTime", date2);
        }
        if (typeid != null && !"".equals(typeid)) {
            paramMap.put("typeid", typeid);
        }

        if (cpInfo != null) {
            paramMap.put("AppId", cpInfo.getAppId());
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.serializeNulls();
            Gson gson = gsonbuilder.create();
            List<WithdrawOrder> allByPage = withdraworderHandler.Allwithdraworder(page, limit, paramMap);
            int count = withdraworderHandler.Allwithdrawordercount(paramMap);
            Map map = new HashMap();
            map.put("code", 0);
            map.put("msg", "");
            map.put("count", count);
            map.put("data", allByPage);
            String s1 = gson.toJson(map);
            output(s1);
        } else {

        }


    }

    public void queryAllwithdraworder() {
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all = withdraworderHandler.getAll();
        List<WithdrawOrder> allByPage = withdraworderHandler.queryAllByPageAndLimit(page, limit);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", all.size());
        map.put("data", allByPage);
        String s1 = gson.toJson(map);
        output(s1);

    }

    public void queryAllByPageAndLimit() {
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all = withdraworderHandler.getAll();
        List<WithdrawOrder> allByPage = withdraworderHandler.queryAllByPageAndLimit(page, limit);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", all.size());
        map.put("data", allByPage);
        String s1 = gson.toJson(map);
        output(s1);

    }

    public void querycondition() throws ParseException {

        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        Map conmap = new HashMap();
        String idstr = this.servletRequest.getParameter("Id");
        String type = this.servletRequest.getParameter("typeid");
        String orderId = this.servletRequest.getParameter("OrderId");
        String confirmstatus = this.servletRequest.getParameter("ConfirmStatus");
        String createTimee = this.servletRequest.getParameter("CreateTime");

        int rowIndex = PageUtils.getRowIndex(page, limit);
        conmap.put("rowIndex", rowIndex);
        conmap.put("limit", limit);
        if (!"".equals(idstr) && idstr != null) {
            Long id = Long.parseLong(idstr);
            conmap.put("id", id);
        }
        if (!"".equals(type) && type != null) {

            conmap.put("typeid", Integer.parseInt(type));
        }
        conmap.put("orderId", orderId);

        if (!"".equals(confirmstatus) && confirmstatus != null) {
            int status = Integer.parseInt(confirmstatus);
            conmap.put("confirmstatus", status);
        }
        List<WithdrawOrder> list = new ArrayList<>();
        String earlyDate = this.servletRequest.getParameter("earlyDate");
        String lateDate = this.servletRequest.getParameter("lateDate");

        if (earlyDate != null && !"".equals(earlyDate) && lateDate != null && !"".equals(lateDate)) {
            if (earlyDate.equals(lateDate)) {
                conmap.put("CreateDay", earlyDate);
                list = withdraworderHandler.querycondition(conmap);
                System.out.println("1");
            } else {
                conmap.put("earlyDate", earlyDate);
                conmap.put("lateDate", lateDate);
                list = withdraworderHandler.querywithdraworderdates(conmap);
                System.out.println("2");
            }
        } else if (earlyDate == null || "".equals(earlyDate)) {
            conmap.put("CreateDay", lateDate);
            list = withdraworderHandler.querycondition(conmap);
            System.out.println("3");
        } else if (lateDate == null || "".equals(lateDate)) {
            conmap.put("CreateDay", earlyDate);
            list = withdraworderHandler.querycondition(conmap);
            System.out.println("4");
        } else {
            list = withdraworderHandler.querycondition(conmap);
            System.out.println("5");
        }

        int count = withdraworderHandler.withdrawcount(conmap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m = new HashMap();
        m.put("code", 0);
        m.put("msg", "");
        m.put("count", count);
        m.put("data", list);
        String s1 = gson.toJson(m);
        output(s1);

    }


    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        WithdrawOrderAction.sdf = sdf;
    }

    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public WithdrawTypeHandler getWithdrawTypeHandler() {
        return withdrawTypeHandler;
    }

    public MoneylogHandler getMoneylogHandler() {
        return moneylogHandler;
    }

    public void setMoneylogHandler(MoneylogHandler moneylogHandler) {
        this.moneylogHandler = moneylogHandler;
    }

    public void setWithdrawTypeHandler(WithdrawTypeHandler withdrawTypeHandler) {
        this.withdrawTypeHandler = withdrawTypeHandler;
    }

    public WithdrawOrderHandler getWithdraworderHandler() {
        return withdraworderHandler;
    }

    public void setWithdraworderHandler(WithdrawOrderHandler withdraworderHandler) {
        this.withdraworderHandler = withdraworderHandler;
    }

    public CpstatOwnHandler getCpstatownHandler() {
        return cpstatownHandler;
    }

    public void setCpstatownHandler(CpstatOwnHandler cpstatownHandler) {
        this.cpstatownHandler = cpstatownHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

}
