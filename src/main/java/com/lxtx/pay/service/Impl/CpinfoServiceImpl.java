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
import com.lxtx.pay.pojo.FailInfo;
import com.lxtx.pay.pojo.LoginLog;
import com.lxtx.pay.pojo.VerifyCodeEntry;
import com.lxtx.pay.service.CpinfoService;
import com.lxtx.pay.utils.CommonUtil;
import com.lxtx.pay.utils.GoogleAuthenticator;
import com.lxtx.pay.utils.TelegramUtils;
import com.lxtx.pay.utils.VerificationCodeGenerator;
import com.lxtx.pay.vo.CpHomeStaticticsVO;
import com.lxtx.pay.vo.CpInfoRemainVO;
import com.lxtx.pay.vo.CpInfoSettingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CpinfoServiceImpl implements CpinfoService {
    @Autowired
    private CpInfoHandler cpInfoHandler;
    @Autowired
    private LoginLogHandler loginLogHandler;
    // key: username, value: FailInfo
    private final Map<String, FailInfo> loginFailMap = new ConcurrentHashMap<>();

    private final Map<String, VerifyCodeEntry> tokenAndVerifyCodeMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public CpinfoServiceImpl() {

        // 定期清理过期的验证码条目
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            tokenAndVerifyCodeMap.entrySet().removeIf(entry -> entry.getValue().getExpireAt() < now);
        }, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public int login(HttpServletRequest request, CpinfoReqDTO reqDTO) {
        LocalDate today = LocalDate.now();
        // 获取失败信息
        FailInfo failInfo = loginFailMap.get(reqDTO.getUsername());
        if (failInfo != null) {
            if (failInfo.getLastFailDate().equals(today) && failInfo.getCount() >= 5) {
                try {
                    TelegramUtils.replyAsync("用户(" + reqDTO.getUsername() + ")频繁登录，登录次数大于5次，IP:" + getRemortIP(request) + ",若不是我方用户请拉黑处理～", null);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                return -1;
            }
        }
        String userName = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = this.cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + userName + "),用户信息错误", null);
            setUlserLoginCatch(failInfo, reqDTO.getUsername());
            return -1;
        } else {
            String userPass = cpInfo.getUserPass();
            if (userPass.equals(password) && cpInfo.getStatus() == 1) {
                String googleSecret = cpInfo.getGoogleSecret();
                if (StringUtils.isNotEmpty(googleSecret)) {
                    if (StringUtils.isEmpty(reqDTO.getGoogleCode())) {
                        TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + userName + "),谷歌验证码为空", null);
                        setUlserLoginCatch(failInfo, reqDTO.getUsername());
                        return -2;
                    }

                    GoogleAuthenticator ga = new GoogleAuthenticator();
                    boolean b = ga.check_code(googleSecret, Long.parseLong(reqDTO.getGoogleCode()), System.currentTimeMillis());
                    TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + userName + "),谷歌验证码错误", null);
                    setUlserLoginCatch(failInfo, reqDTO.getUsername());
                    if (!b) {
                        return -2;
                    }
                }
                TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + userName + "),登录成功", cpInfo.getTgId());
                TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + userName + "),登录成功", null);
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
                // 登录成功：清除记录
                if (loginFailMap.containsKey(userName))
                    loginFailMap.remove(userName);
                return 1;
            } else {
                setUlserLoginCatch(failInfo, reqDTO.getUsername());
                return 0;
            }
        }
    }


    public void setUlserLoginCatch(FailInfo failInfo, String username) {
        try {
            if (failInfo == null || !failInfo.getLastFailDate().equals(LocalDate.now())) {
                loginFailMap.put(username, new FailInfo(1, LocalDate.now()));
            } else {
                failInfo.setCount(failInfo.getCount() + 1);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject loginV2(HttpServletRequest request, CpinfoReqDTO reqDTO) {
        JSONObject response = new JSONObject();

        LocalDate today = LocalDate.now();

        // 获取失败信息
        FailInfo failInfo = loginFailMap.get(reqDTO.getUsername());
        if (failInfo != null) {
            if (failInfo.getLastFailDate().equals(today) && failInfo.getCount() >= 5) {
                try {
                    TelegramUtils.replyAsync("用户(" + reqDTO.getUsername() + ")频繁登录，登录次数大于5次，IP:" + getRemortIP(request) + ",若不是我方用户请拉黑处理～", null);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                response.put("code", -1);
                response.put("msg", "登录账号异常");
                return response;
            }
        }
        String username = reqDTO.getUsername();
        String password = reqDTO.getPassword();
        CpInfo cpInfo = this.cpInfoHandler.queryOne(reqDTO);
        if (cpInfo == null) {
            setUlserLoginCatch(failInfo, reqDTO.getUsername());
            response.put("code", -1);
            response.put("msg", "登录账号不存在");
            TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + ")返回信息" + response.toJSONString(), null);
            return response;
        } else {
            boolean isTrue = cpInfo.getUserPass().equals(password);
            if (!isTrue) {
                setUlserLoginCatch(failInfo, reqDTO.getUsername());
                response.put("code", -1);
                response.put("msg", "密码错误");
                TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + ")返回信息" + response.toJSONString(), null);
                return response;
            } else {
                String googleSecret = cpInfo.getGoogleSecret();
                if (StringUtils.isNotEmpty(googleSecret)) {
                    if (StringUtils.isEmpty(reqDTO.getGoogleCode())) {
                        setUlserLoginCatch(failInfo, reqDTO.getUsername());
                        response.put("code", -1);
                        response.put("msg", "请输入谷歌验证码");
                        TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + ")返回信息" + response.toJSONString(), null);
                        return response;
                    } else {
                        GoogleAuthenticator ga = new GoogleAuthenticator();
                        boolean b = ga.check_code(googleSecret, Long.parseLong(reqDTO.getGoogleCode()), System.currentTimeMillis());
                        if (!b) {
                            setUlserLoginCatch(failInfo, reqDTO.getUsername());
                            response.put("code", -1);
                            response.put("msg", "谷歌验证码校验错误");
                            TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + ")返回信息" + response.toJSONString(), null);
                            return response;
                        } else {
                            TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + "),登录成功", null);
                            TelegramUtils.replyAsync("商户后台登录提醒:登录ip(" + getRemortIP(request) + "),登录用户名(" + username + "),登录成功", cpInfo.getTgId());
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
                            if (loginFailMap.containsKey(username))
                                loginFailMap.remove(username);
                            return response;
                        }
                    }
                } else {
                    setUlserLoginCatch(failInfo, reqDTO.getUsername());
                    request.getSession().setAttribute("cpInfo", cpInfo);

                    response.put("code", -2);
                    response.put("msg", "首次登录请生成并绑定谷歌秘钥");
                    return response;
                }
            }
        }
    }

    public static String getRemortIP(HttpServletRequest request) {
        String userIP = request.getHeader("userIP"); // Cloudflare优先
        if (StringUtils.isNotEmpty(userIP) && !"unKnown".equalsIgnoreCase(userIP)) {
            return getFirstIp(userIP);
        }

        String ip = request.getHeader("CF-Connecting-IP"); // Cloudflare优先
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return getFirstIp(ip);
        }

        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return getFirstIp(ip);
        }

        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }

    private static String getFirstIp(String ip) {
        if (ip == null || ip.length() == 0) {
            return ip;
        }

        // 处理多个IP的情况（如：192.168.1.1, 192.168.1.2）
        String[] ips = ip.split("\\s*,\\s*");
        return ips[0];
    }

    private static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        cpInfo.setSessionId("");
        this.cpInfoHandler.setSessionId(cpInfo);
        request.getSession().removeAttribute("cpInfo");
    }

    @Override
    public int changePassword(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        String passwordOri = reqDTO.getPasswordOri();
        String passwordNew = reqDTO.getPasswordNew();
        CpInfo c = (CpInfo) this.cpInfoHandler.select(cpInfo.getAppId());
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
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
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
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
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
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        cpInfo = (CpInfo) this.cpInfoHandler.select(cpInfo.getAppId());
        resultJson.put("appId", cpInfo.getAppId());
        resultJson.put("googleSecret", cpInfo.getGoogleSecret());
        return resultJson;
    }

    @Override
    public JSONObject getHomePageInfo(HttpServletRequest request) {

        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");

        String payFeeRate = cpInfo.getPayFeeRate().multiply(new BigDecimal(100)) + "%";
        String payFeeFix = new BigDecimal(cpInfo.getPayFeeFix()).multiply(new BigDecimal("0.01")) + "";

        String withdrawFeeRate = cpInfo.getWithdrawFeeRate().multiply(new BigDecimal(100)) + "%";
        String withdrawFeeFix = new BigDecimal(cpInfo.getWithdrawFeeFix()).multiply(new BigDecimal("0.01")) + "";

        JSONObject result = new JSONObject();
        result.put("key", cpInfo.getKey());
        result.put("payFeeRate", payFeeRate);
        result.put("payFeeFix", payFeeFix);
        result.put("withdrawFeeRate", withdrawFeeRate);
        result.put("withdrawFeeFix", withdrawFeeFix);

        int appId = cpInfo.getAppId();
        CpInfo info = cpInfoHandler.select(appId);
        String publicKey = info.getPublicKey();
        log.info(cpInfo.getAppId() + " " + publicKey);

        if (StringUtils.isEmpty(publicKey)) {
            result.put("hasPublicKey", false);
        } else {
            result.put("hasPublicKey", true);
        }

        return result;
    }

    @Override
    public JSONObject generateToken() {
        JSONObject jsonObject = new JSONObject();
        System.out.println("生成验证码");

        try {
            VerificationCodeGenerator.VerificationCodeResult result = VerificationCodeGenerator.generateVerificationCode();
            System.out.println("验证码: " + result);

            String uuidToken = UUID.randomUUID().toString();

            long expireAt = System.currentTimeMillis() + 5 * 60 * 1000; // 5分钟后过期
            tokenAndVerifyCodeMap.put(uuidToken, new VerifyCodeEntry(result.getCode(), expireAt));

            jsonObject.put("token", uuidToken);
            jsonObject.put("base64Image", "data:image/png;base64," + result.getBase64Image());
        }catch (Exception e) {
            log.error("e: ", e);
        }

        return jsonObject;
    }

    @Override
    public boolean checkExistGoogle(String username) {
        CpinfoReqDTO cpinfoReqDTO = new CpinfoReqDTO();
        cpinfoReqDTO.setUsername(username);

        CpInfo cpInfo = cpInfoHandler.queryOne(cpinfoReqDTO);
        return cpInfo != null && StringUtils.isNotEmpty(cpInfo.getGoogleSecret());
    }

    @Override
    public boolean checkVerifyCode(String uuid, String verifyCode) {
        VerifyCodeEntry verifyCodeEntry = tokenAndVerifyCodeMap.get(uuid);

        String code = verifyCodeEntry.getCode();
        long expireAt = verifyCodeEntry.getExpireAt();

        if (System.currentTimeMillis() <= expireAt && code.equalsIgnoreCase(verifyCode)) {
            // 验证通过后移除，防止重复使用
            tokenAndVerifyCodeMap.remove(uuid);
            return true;
        }

        return false;
    }


    @Override
    public JSONObject createCpInfoSecret(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        String secretKey = GoogleAuthenticator.generateSecretKey();
        CpInfoSettingReqDTO cpInfoSettingReqDTO = new CpInfoSettingReqDTO();
        cpInfoSettingReqDTO.setAppId(cpInfo.getAppId() + "");
        cpInfoSettingReqDTO.setGoogleSecret(secretKey + "");
        int i = this.cpInfoHandler.updateCpInfoGoogleSecret(cpInfoSettingReqDTO);
        if (i > 0) {
            request.getSession().removeAttribute("cpInfo");
            JSONObject data = new JSONObject();
            data.put("googleSecret", secretKey);
            data.put("qrBarcode", "otpauth://totp/inshoppay?secret=" + secretKey + "&issuer=inshoppay");
            return data;
        } else {
            return null;
        }
    }

    @Override
    public CpInfoSettingVO resetPayKey(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        String googleSecret = cpInfo.getGoogleSecret();
        if (StringUtils.isNotEmpty(googleSecret)) {
            if (StringUtils.isEmpty(reqDTO.getGoogleSecret())) {
                return null;
            } else {
                GoogleAuthenticator ga = new GoogleAuthenticator();
                boolean b = ga.check_code(googleSecret, Long.parseLong(reqDTO.getGoogleSecret()), System.currentTimeMillis());
                if (b) {
                    CpInfoSettingReqDTO cpInfoSettingReqDTO = new CpInfoSettingReqDTO();
                    cpInfoSettingReqDTO.setAppId(cpInfo.getAppId() + "");
                    cpInfoSettingReqDTO.setPublicKey(reqDTO.getPublicKey().replace("\\u003d", "=").replace("-----BEGIN PRIVATE KEY-----", "")
                            .replace("-----END PRIVATE KEY-----", "")
                            .replaceAll("\\s+", ""));
                    int i = this.cpInfoHandler.updateCpInfoPaykey(cpInfoSettingReqDTO);
                    if (i > 0) {
                        CpInfoSettingVO cpInfoSettingVO = new CpInfoSettingVO();
                        return cpInfoSettingVO;
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;

    }


    public static JSONObject getRsaKey() {
        JSONObject jsonObject = new JSONObject();
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");

            keyGen.initialize(2048); // 可选：1024 / 2048 / 4096
            KeyPair pair = keyGen.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

            jsonObject.put("publicKey", publicKey);
            jsonObject.put("privateKey", privateKey);
            return jsonObject;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
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
