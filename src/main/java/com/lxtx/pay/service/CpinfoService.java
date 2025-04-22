package com.lxtx.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.CpHomeStaticticsReqDTO;
import com.lxtx.pay.dto.CpInfoSettingReqDTO;
import com.lxtx.pay.dto.CpinfoReqDTO;
import com.lxtx.pay.vo.CpHomeStaticticsVO;
import com.lxtx.pay.vo.CpInfoRemainVO;
import com.lxtx.pay.vo.CpInfoSettingVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public interface CpinfoService {

    int changePassword(HttpServletRequest var1, CpInfoSettingReqDTO var2);
    CpInfoSettingVO resetPayKey(HttpServletRequest var1, CpInfoSettingReqDTO var2);
    JSONObject createCpInfoSecret(HttpServletRequest var1);

    JSONObject createCpInfoSecret(CpinfoReqDTO var1);

    int login(HttpServletRequest var1, CpinfoReqDTO var2);

    JSONObject loginV2(HttpServletRequest var1, CpinfoReqDTO var2);

    void logout(HttpServletRequest var1, HttpServletResponse var2);

    CpHomeStaticticsVO getCpHomeStatictics(HttpServletRequest var1, CpHomeStaticticsReqDTO var2) throws ParseException;

    CpInfoRemainVO getCpInfoRemain(HttpServletRequest var1);

    JSONObject getGoogleSecret(HttpServletRequest var1);

}
