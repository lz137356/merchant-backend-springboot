package com.lxtx.pay.handler;

import com.lxtx.pay.dto.CpHomeStaticticsReqDTO;
import com.lxtx.pay.dto.CpInfoFrozenRemainReqDTO;
import com.lxtx.pay.dto.CpInfoSettingReqDTO;
import com.lxtx.pay.dto.CpinfoReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.vo.CpHomeStaticticsVO;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;

import java.util.Map;

public class CpInfoHandler extends SimpleIbatisEntityHandler<CpInfo> {

    public int frozenCpInfoRemain(CpInfoFrozenRemainReqDTO reqDTO) {
        return update("frozenCpInfoRemain", reqDTO);
    }

    public int updateCpInfoGoogleSecret(CpInfoSettingReqDTO reqDTO) {
        return update("updateCpInfoGoogleSecret", reqDTO);
    }

    public int updateCpInfoPassword(CpInfoSettingReqDTO reqDTO) {
        return update("updateCpInfoPassword", reqDTO);
    }

    public CpInfo queryOne(CpinfoReqDTO reqDTO) {
        return queryForObject("cpinfoIsExist", reqDTO);
    }

    public int setSessionId(CpInfo cpInfo) {
        return update("setSessionId", cpInfo);
    }

    public CpHomeStaticticsVO getCpPayHomeStatictics(CpHomeStaticticsReqDTO reqDTO) {
        return queryForObject("getCpPayHomeStatictics", reqDTO);
    }

    public CpHomeStaticticsVO getCpWithdrawHomeStatictics(CpHomeStaticticsReqDTO reqDTO) {
        return queryForObject("getCpWithdrawHomeStatictics", reqDTO);
    }

    public CpInfo getCpInfoRemain(Integer appId) {
        return select(appId);
    }


}
