package com.lxtx.pay.helper;

import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.pojo.Paylog;

import java.util.HashMap;
import java.util.Map;

public class PaylogHelper  extends BaseHelper{
    private PaylogHandler paylogHandler;
    private Map<String, Paylog> hashmap=new HashMap<>();


    public PaylogHandler getPaylogHandler() {
        return paylogHandler;
    }

    public void setPaylogHandler(PaylogHandler paylogHandler) {
        this.paylogHandler = paylogHandler;
    }

    public Map<String, Paylog> getHashmap() {
        return hashmap;
    }

    public void setHashmap(Map<String, Paylog> hashmap) {
        this.hashmap = hashmap;
    }

    @Override
    protected void realInit() {


        
        
        
        
    }
}
