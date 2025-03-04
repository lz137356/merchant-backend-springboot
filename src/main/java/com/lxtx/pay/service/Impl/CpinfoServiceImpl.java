//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpinfoServiceImpl implements CpinfoService {
    @Autowired
    private CpInfoHandler cpInfoHandler;
    @Autowired
    private LoginLogHandler loginLogHandler;

    public CpinfoServiceImpl() {
    }

    @Override
    public int login(HttpServletRequest request, CpinfoReqDTO reqDTO) {
        String userName = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = this.cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            return -1;
        } else {
            String userPass = cpInfo.getUserPass();
            if (userPass.equals(password) && cpInfo.getStatus() == 1) {
                String googleSecret = cpInfo.getGoogleSecret();
                if (StringUtils.isNotEmpty(googleSecret)) {
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
                this.loginLogHandler.add(loginLog);
                cpInfo.setSessionId(request.getSession().getId());
                this.cpInfoHandler.setSessionId(cpInfo);
                request.getSession().setAttribute("cpInfo", cpInfo);
                request.getSession().setMaxInactiveInterval(21600);
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public JSONObject loginV2(HttpServletRequest request, CpinfoReqDTO reqDTO) {
        JSONObject response = new JSONObject();
        String username = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = this.cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            response.put("code", -1);
            response.put("msg", "登录账号不存在");
            return response;
        } else {
            boolean isTrue = cpInfo.getUserPass().equals(password);
            if (!isTrue) {
                response.put("code", -1);
                response.put("msg", "密码错误");
                return response;
            } else {
                String googleSecret = cpInfo.getGoogleSecret();
                if (StringUtils.isNotEmpty(googleSecret)) {
                    if (StringUtils.isEmpty(reqDTO.getGoogleCode())) {
                        response.put("code", -1);
                        response.put("msg", "请输入谷歌验证码");
                        return response;
                    } else {
                        GoogleAuthenticator ga = new GoogleAuthenticator();
                        boolean b = ga.check_code(googleSecret, Long.parseLong(reqDTO.getGoogleCode()), System.currentTimeMillis());
                        if (!b) {
                            response.put("code", -1);
                            response.put("msg", "谷歌验证码校验错误");
                            return response;
                        } else {
                            response.put("code", 1);
                            response.put("msg", "密码和谷歌验证码正确");
                            LoginLog loginLog = new LoginLog();
                            loginLog.setAppId(cpInfo.getAppId());
                            loginLog.setLogType("登录");
                            loginLog.setDetails("登录");
                            loginLog.setOperationTarget("盘口");
                            loginLog.setRemoteIp(CommonUtil.getRemortIP(request));
                            this.loginLogHandler.add(loginLog);
                            cpInfo.setSessionId(request.getSession().getId());
                            response.put("data", request.getSession().getId());
                            this.cpInfoHandler.setSessionId(cpInfo);
                            request.getSession().setAttribute("cpInfo", cpInfo);
                            request.getSession().setMaxInactiveInterval(21600);
                            return response;
                        }
                    }
                } else {
                    response.put("code", -2);
                    response.put("msg", "首次登录请生成并绑定谷歌秘钥");
                    return response;
                }
            }
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        cpInfo.setSessionId("");
        this.cpInfoHandler.setSessionId(cpInfo);
        request.getSession().removeAttribute("cpInfo");
    }

    @Override
    public int changePassword(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        String passwordOri = reqDTO.getPasswordOri();
        String passwordNew = reqDTO.getPasswordNew();
        CpInfo c = (CpInfo)this.cpInfoHandler.select(cpInfo.getAppId());
        reqDTO.setAppId(c.getAppId() + "");
        if (passwordOri.equals(c.getUserPass())) {
            int i = this.cpInfoHandler.updateCpInfoPassword(reqDTO);
            if (i > 0) {
                request.getSession().removeAttribute("cpInfo");
                return 1;
            }
        }

        return -1;
    }

    @Override
    public CpHomeStaticticsVO getCpHomeStatictics(HttpServletRequest request, CpHomeStaticticsReqDTO reqDTO) throws ParseException {
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        String country = reqDTO.getCountry();
        reqDTO.setAppId(cpInfo.getAppId() + "");
        CpHomeStaticticsVO cpPayHomeStatictics = this.cpInfoHandler.getCpPayHomeStatictics(reqDTO);
        CpHomeStaticticsVO cpWithdrawHomeStatictics = this.cpInfoHandler.getCpWithdrawHomeStatictics(reqDTO);
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
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        CpInfo cpInfoRemain = this.cpInfoHandler.getCpInfoRemain(cpInfo.getAppId());
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
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        cpInfo = (CpInfo)this.cpInfoHandler.select(cpInfo.getAppId());
        resultJson.put("appId", cpInfo.getAppId());
        resultJson.put("googleSecret", cpInfo.getGoogleSecret());
        return resultJson;
    }

    @Override
    public CpInfoSettingVO createCpInfoSecret(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo)request.getSession().getAttribute("cpInfo");
        String secretKey = GoogleAuthenticator.generateSecretKey();
        CpInfoSettingReqDTO cpInfoSettingReqDTO = new CpInfoSettingReqDTO();
        cpInfoSettingReqDTO.setAppId(cpInfo.getAppId() + "");
        cpInfoSettingReqDTO.setGoogleSecret(secretKey + "");
        int i = this.cpInfoHandler.updateCpInfoGoogleSecret(cpInfoSettingReqDTO);
        if (i > 0) {
            request.getSession().removeAttribute("cpInfo");
            CpInfoSettingVO cpInfoSettingVO = new CpInfoSettingVO();
            cpInfoSettingVO.setGoogleSecret(secretKey);
            return cpInfoSettingVO;
        } else {
            return null;
        }
    }

    @Override
    public JSONObject createCpInfoSecret(CpinfoReqDTO reqDTO) {
        String username = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = this.cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            return null;
        } else {
            String userPass = cpInfo.getUserPass();
            if (userPass.equals(password)) {
                String secretKey = GoogleAuthenticator.generateSecretKey();
                CpInfoSettingReqDTO cpInfoSettingReqDTO = new CpInfoSettingReqDTO();
                cpInfoSettingReqDTO.setAppId(cpInfo.getAppId() + "");
                cpInfoSettingReqDTO.setGoogleSecret(secretKey + "");
                int i = this.cpInfoHandler.updateCpInfoGoogleSecret(cpInfoSettingReqDTO);
                if (i > 0) {
                    JSONObject data = new JSONObject();
                    data.put("googleSecret", secretKey);
                    data.put("qrBarcode", "otpauth://totp/goopay?secret=" + secretKey + "&issuer=goopay.online");
                    return data;
                }
            }

            return null;
        }
    }
}
