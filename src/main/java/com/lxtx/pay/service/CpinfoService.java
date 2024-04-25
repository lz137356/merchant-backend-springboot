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

    int changePassword(HttpServletRequest request, CpInfoSettingReqDTO reqDTO);

    CpInfoSettingVO createCpInfoSecret(HttpServletRequest request);

    int login(HttpServletRequest request, CpinfoReqDTO reqDTO);

    void logout(HttpServletRequest request, HttpServletResponse response);

    CpHomeStaticticsVO getCpHomeStatictics(HttpServletRequest request, CpHomeStaticticsReqDTO reqDTO) throws ParseException;

    CpInfoRemainVO getCpInfoRemain(HttpServletRequest request);

    JSONObject getGoogleSecret(HttpServletRequest request);

}
