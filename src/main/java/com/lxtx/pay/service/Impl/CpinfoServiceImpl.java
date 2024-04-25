package com.lxtx.pay.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.CpHomeStaticticsReqDTO;
import com.lxtx.pay.dto.CpInfoSettingReqDTO;
import com.lxtx.pay.dto.CpinfoReqDTO;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.LoginLogHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.LoginLog;
import com.lxtx.pay.service.CpinfoService;
import com.lxtx.pay.utils.CommonUtil;
import com.lxtx.pay.utils.GoogleAuthenticator;
import com.lxtx.pay.vo.CpHomeStaticticsVO;
import com.lxtx.pay.vo.CpInfoRemainVO;
import com.lxtx.pay.vo.CpInfoSettingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;

@Service
public class CpinfoServiceImpl implements CpinfoService {

    @Autowired
    private CpInfoHandler cpInfoHandler;

    @Autowired
    private LoginLogHandler loginLogHandler;

    @Override
    public int login(HttpServletRequest request, CpinfoReqDTO reqDTO) {
//        CpInfo user = (CpInfo) request.getSession().getAttribute("cpInfo");
        String userName = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            return -1;
        } else {
            String userPass = cpInfo.getUserPass();
            if (userPass.equals(password) && cpInfo.getStatus() == 1) {
                String googleSecret = cpInfo.getGoogleSecret();

                if (StringUtils.isNotEmpty(googleSecret) ) {
                    if (StringUtils.isEmpty(reqDTO.getGoogleCode())) {
                        return -2;
                    }

                    GoogleAuthenticator ga = new GoogleAuthenticator();
                    boolean b = ga.check_code(googleSecret, Long.parseLong(reqDTO.getGoogleCode()), System.currentTimeMillis());
                    if (!b) {
                        return -2;
                    }
                }

                LoginLog loginLog = new LoginLog();
                loginLog.setAppId(cpInfo.getAppId());
                loginLog.setLogType("登录");
                loginLog.setDetails("登录");
                loginLog.setOperationTarget("盘口");
                loginLog.setRemoteIp(CommonUtil.getRemortIP(request));
                loginLogHandler.add(loginLog);
                cpInfo.setSessionId(request.getSession().getId());
                cpInfoHandler.setSessionId(cpInfo);

                request.getSession().setAttribute("cpInfo", cpInfo);
                request.getSession().setMaxInactiveInterval(6 * 3600);
                return 1;
                //登录日志
            } else {
                return 0;
            }
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        cpInfo.setSessionId("");
        cpInfoHandler.setSessionId(cpInfo);
        request.getSession().removeAttribute("cpInfo");
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @Override
    public int changePassword(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");

        String passwordOri = reqDTO.getPasswordOri();
        String passwordNew = reqDTO.getPasswordNew();

        CpInfo c = cpInfoHandler.select(cpInfo.getAppId());
        reqDTO.setAppId(c.getAppId() + "");
        if (passwordOri.equals(c.getUserPass())) {
            int i = cpInfoHandler.updateCpInfoPassword(reqDTO);
            if (i > 0) {
                request.getSession().removeAttribute("cpInfo");
                return 1;
            }
        }
        return -1;
    }

    /**
     * 获得首页展示数据
     *
     * @param request
     * @param reqDTO
     * @return
     */
    @Override
    public CpHomeStaticticsVO getCpHomeStatictics(HttpServletRequest request, CpHomeStaticticsReqDTO reqDTO) throws ParseException {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        //String defaultCountry = cpInfo.getCountry();
        String country = reqDTO.getCountry();
        reqDTO.setAppId(cpInfo.getAppId() + "");


        CpHomeStaticticsVO cpPayHomeStatictics = cpInfoHandler.getCpPayHomeStatictics(reqDTO);
        CpHomeStaticticsVO cpWithdrawHomeStatictics = cpInfoHandler.getCpWithdrawHomeStatictics(reqDTO);

        CpHomeStaticticsVO cpHomeStaticticsVO = new CpHomeStaticticsVO();

        cpHomeStaticticsVO.setTotalPayAmount(cpPayHomeStatictics.getTotalPayAmount());
        cpHomeStaticticsVO.setTotalPayFee(cpPayHomeStatictics.getTotalPayFee());
        cpHomeStaticticsVO.setTotalPayNum(cpPayHomeStatictics.getTotalPayNum());

        cpHomeStaticticsVO.setTodayPayAmount(cpPayHomeStatictics.getTodayPayAmount());
        cpHomeStaticticsVO.setTodayPayFee(cpPayHomeStatictics.getTodayPayFee());
        cpHomeStaticticsVO.setTodayPayNum(cpPayHomeStatictics.getTodayPayNum());

        cpHomeStaticticsVO.setYesterdayPayAmount(cpPayHomeStatictics.getYesterdayPayAmount());
        cpHomeStaticticsVO.setYesterdayPayFee(cpPayHomeStatictics.getYesterdayPayFee());
        cpHomeStaticticsVO.setYesterdayPayNum(cpPayHomeStatictics.getYesterdayPayNum());

        cpHomeStaticticsVO.setTotalWithdrawAmount(cpWithdrawHomeStatictics.getTotalWithdrawAmount());
        cpHomeStaticticsVO.setTotalWithdrawFee(cpWithdrawHomeStatictics.getTotalWithdrawFee());
        cpHomeStaticticsVO.setTotalWithdrawNum(cpWithdrawHomeStatictics.getTotalWithdrawNum());

        cpHomeStaticticsVO.setTodayWithdrawAmount(cpWithdrawHomeStatictics.getTodayWithdrawAmount());
        cpHomeStaticticsVO.setTodayWithdrawFee(cpWithdrawHomeStatictics.getTodayWithdrawFee());
        cpHomeStaticticsVO.setTodayWithdrawNum(cpWithdrawHomeStatictics.getTodayWithdrawNum());

        cpHomeStaticticsVO.setYesterdayWithdrawAmount(cpWithdrawHomeStatictics.getYesterdayWithdrawAmount());
        cpHomeStaticticsVO.setYesterdayWithdrawFee(cpWithdrawHomeStatictics.getYesterdayWithdrawFee());
        cpHomeStaticticsVO.setYesterdayWithdrawNum(cpWithdrawHomeStatictics.getYesterdayWithdrawNum());

        return cpHomeStaticticsVO;
    }

    @Override
    public CpInfoRemainVO getCpInfoRemain(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        CpInfo cpInfoRemain = cpInfoHandler.getCpInfoRemain(cpInfo.getAppId());

        CpInfoRemainVO cpInfoRemainVO = new CpInfoRemainVO();
        cpInfoRemainVO.setRemain(cpInfoRemain.getRemain());
        cpInfoRemainVO.setActualRemain(cpInfoRemain.getActualRemain().longValue());
        cpInfoRemainVO.setSettleRemain(cpInfoRemain.getRemain() - cpInfoRemain.getActualRemain().longValue());
        cpInfoRemainVO.setCountry(cpInfoRemain.getCountry());

        return cpInfoRemainVO;
    }

    @Override
    public JSONObject getGoogleSecret(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();

        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        cpInfo = cpInfoHandler.select(cpInfo.getAppId());

        resultJson.put("appId", cpInfo.getAppId());
        resultJson.put("googleSecret", cpInfo.getGoogleSecret());

        return resultJson;
    }

    @Override
    public CpInfoSettingVO createCpInfoSecret(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        String secretKey = GoogleAuthenticator.generateSecretKey();

        CpInfoSettingReqDTO cpInfoSettingReqDTO = new CpInfoSettingReqDTO();
        cpInfoSettingReqDTO.setAppId(cpInfo.getAppId() + "");
        cpInfoSettingReqDTO.setGoogleSecret(secretKey + "");
        int i = cpInfoHandler.updateCpInfoGoogleSecret(cpInfoSettingReqDTO);
        if (i > 0) {
            request.getSession().removeAttribute("cpInfo");
            CpInfoSettingVO cpInfoSettingVO = new CpInfoSettingVO();
            cpInfoSettingVO.setGoogleSecret(secretKey);
            return cpInfoSettingVO;
        }
        return null;
    }
}
