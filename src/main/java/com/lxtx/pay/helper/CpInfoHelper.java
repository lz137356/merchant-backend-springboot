package com.lxtx.pay.helper;

import com.lxtx.pay.handler.CpstatOwnHandler;

public class CpInfoHelper extends BaseHelper{



private CpstatOwnHandler cpstatOwnHandler;

    public CpstatOwnHandler getCpstatOwnHandler() {
        return cpstatOwnHandler;
    }

    public void setCpstatOwnHandler(CpstatOwnHandler cpstatOwnHandler) {
        this.cpstatOwnHandler = cpstatOwnHandler;
    }

    @Override
    protected void realInit() {

    }
}
