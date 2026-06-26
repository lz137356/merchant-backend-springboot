package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.config.PlatformProperties;
import com.lxtx.pay.dto.CheckPasswordReqDTO;
import com.lxtx.pay.dto.CpHomeStaticticsReqDTO;
import com.lxtx.pay.dto.CpInfoSettingReqDTO;
import com.lxtx.pay.dto.CpinfoReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.CpinfoService;
import com.lxtx.pay.vo.CpHomeStaticticsVO;
import com.lxtx.pay.vo.CpInfoRemainVO;
import com.lxtx.pay.vo.CpInfoSettingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

@Controller
@RequestMapping("/pay/cpinfo")
public class CpinfoController {

    @Autowired
    private CpinfoService cpinfoService;

    @Autowired
    private PlatformProperties platformProperties;

    @RequestMapping("/changePassword")
    @ResponseBody
    public JSONObject changePassword(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        int i = cpinfoService.changePassword(request, reqDTO);
        if (i > 0) {
            return Result.success("修改成功。");
        } else {
            return Result.fail("修改失败。");
        }
    }


    @RequestMapping("/resetPayKey")
    @ResponseBody
    public JSONObject resetPayKey(HttpServletRequest request, CpInfoSettingReqDTO reqDTO) {
        CpInfoSettingVO cpInfoSecret = cpinfoService.resetPayKey(request, reqDTO);
        if (cpInfoSecret != null) {
            return Result.success(cpInfoSecret);
        } else {
            return Result.fail("重置失败。请检查验证码是否正确");
        }
    }

    @RequestMapping("/createGoogleSecret")
    @ResponseBody
    public JSONObject createGoogleSecret(HttpServletRequest request) {
        JSONObject cpInfoSecret = cpinfoService.createCpInfoSecret(request);
        if (cpInfoSecret != null) {
            return Result.success(cpInfoSecret);
        }
        return Result.fail("生成谷歌秘钥失败");
    }

    @RequestMapping({"/login"})
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody(required = false) String body,
                            CpinfoReqDTO reqDTO) throws IOException {
        // 根据 platform.version 配置区分前端版本
        if (platformProperties.isV6()) {
            // v6 (vvpay): JSON 格式，需要验证码
            if (body == null || body.isEmpty()) {
                return Result.fail("请求体不能为空");
            }
            JSONObject json = JSON.parseObject(body);
            String token = json.getString("token");
            String inputVerifyCode = json.getString("inputVerifyCode");
            if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(inputVerifyCode)) {
                if (!cpinfoService.checkVerifyCode(token, inputVerifyCode)) {
                    return Result.fail("验证码错误");
                }
            }
            CpinfoReqDTO dto = new CpinfoReqDTO();
            dto.setUsername(json.getString("username"));
            dto.setPassword(json.getString("password"));
            dto.setGoogleCode(json.getString("googleCode"));
            return this.cpinfoService.loginV2(request, dto);
        } else {
            // v5 (velopay): 表单提交
            return this.cpinfoService.loginV2(request, reqDTO);
        }
    }

    @RequestMapping({"/login_v3"})
    @ResponseBody
    public JSONObject login_v3(HttpServletRequest request, @RequestBody String body) throws IOException {
        JSONObject bodyJson = JSON.parseObject(body);

        String token = bodyJson.getString("token");

        String verifyCode = bodyJson.getString("inputVerifyCode");

        boolean b = cpinfoService.checkVerifyCode(token, verifyCode);
        if (!b) {
            return Result.fail("验证码错误");
        }

        CpinfoReqDTO cpinfoReqDTO = new CpinfoReqDTO();
        cpinfoReqDTO.setUsername(bodyJson.getString("username"));
        cpinfoReqDTO.setPassword(bodyJson.getString("password"));
        cpinfoReqDTO.setGoogleCode(bodyJson.getString("googleCode"));

        return this.cpinfoService.loginV2(request, cpinfoReqDTO);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JSONObject logout(HttpServletRequest request, HttpServletResponse response, CpinfoReqDTO reqDTO) throws IOException {
        cpinfoService.logout(request, response);
        return Result.success("商户退出。");
    }

    @RequestMapping("/getCpHomeStatictics")
    @ResponseBody
    public JSONObject getCpHomeStatictics(HttpServletRequest request, CpHomeStaticticsReqDTO reqDTO) throws ParseException {
        CpHomeStaticticsVO cpHomeStatictics = cpinfoService.getCpHomeStatictics(request, reqDTO);
        return Result.success(cpHomeStatictics);
    }

    @RequestMapping("/getCpInfoRemain")
    @ResponseBody
    public JSONObject getCpInfoRemain(HttpServletRequest request) {
        CpInfoRemainVO cpInfoRemain = cpinfoService.getCpInfoRemain(request);
        return Result.success(cpInfoRemain);
    }

    @RequestMapping("/checkPassword")
    @ResponseBody
    public JSONObject checkPassword(HttpServletRequest request, CheckPasswordReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        String userPass = cpInfo.getUserPass();
        if (reqDTO.getPassword().equals(userPass)) {
            return Result.success("true", null);
        } else {
            return Result.fail("wrong");
        }
    }

    @RequestMapping("/getSessionCpInfo")
    @ResponseBody
    public JSONObject getSessionCpInfo(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        CpInfoSettingVO cpInfoSettingVO = new CpInfoSettingVO();
        cpInfoSettingVO.setAppId(cpInfo.getAppId() + "");
        cpInfoSettingVO.setKey(cpInfo.getKey());
        cpInfoSettingVO.setGoogleSecret(cpInfo.getGoogleSecret());
        cpInfoSettingVO.setUserName(cpInfo.getUserName());
        cpInfoSettingVO.setCountry(cpInfo.getCountry());

        String payFeeRate = cpInfo.getPayFeeRate().multiply(new BigDecimal(100)) + "%";
        String payFeeFix = new BigDecimal(cpInfo.getPayFeeFix()).multiply(new BigDecimal("0.01")) + "";

        String withdrawFeeRate = cpInfo.getWithdrawFeeRate().multiply(new BigDecimal(100)) + "%";
        String withdrawFeeFix = new BigDecimal(cpInfo.getWithdrawFeeFix()).multiply(new BigDecimal("0.01")) + "";

        cpInfoSettingVO.setPayFeeRate(payFeeRate);
        cpInfoSettingVO.setPayFeeFix(payFeeFix);
        cpInfoSettingVO.setWithdrawFeeRate(withdrawFeeRate);
        cpInfoSettingVO.setWithdrawFeeFix(withdrawFeeFix);

        return Result.success(cpInfoSettingVO);
    }

    /**
     * VVPay (v6) 使用 /me 替代 /getSessionCpInfo，字段命名略有不同
     */
    @RequestMapping("/me")
    @ResponseBody
    public JSONObject me(HttpServletRequest request) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            return Result.fail("未登录");
        }
        // 复用 getSessionCpInfo 逻辑，但添加 userNo 字段兼容 v6
        JSONObject result = new JSONObject();
        result.put("userNo", cpInfo.getAppId());  // v6 使用 userNo
        result.put("appId", cpInfo.getAppId());
        result.put("key", cpInfo.getKey());
        result.put("googleSecret", cpInfo.getGoogleSecret());
        result.put("userName", cpInfo.getUserName());
        result.put("country", cpInfo.getCountry());

        String payFeeRate = cpInfo.getPayFeeRate().multiply(new BigDecimal(100)) + "%";
        String payFeeFix = new BigDecimal(cpInfo.getPayFeeFix()).multiply(new BigDecimal("0.01")) + "";
        String withdrawFeeRate = cpInfo.getWithdrawFeeRate().multiply(new BigDecimal(100)) + "%";
        String withdrawFeeFix = new BigDecimal(cpInfo.getWithdrawFeeFix()).multiply(new BigDecimal("0.01")) + "";

        result.put("payFeeRate", payFeeRate);
        result.put("payFeeFix", payFeeFix);
        result.put("withdrawFeeRate", withdrawFeeRate);
        result.put("withdrawFeeFix", withdrawFeeFix);

        return Result.success(result);
    }


    @RequestMapping("/getGoogleSecret")
    @ResponseBody
    public JSONObject getGoogleSecret(HttpServletRequest request) {
        JSONObject googleSecret = cpinfoService.getGoogleSecret(request);

        return Result.success(googleSecret);
    }

    @RequestMapping("/getHomePageInfo")
    @ResponseBody
    public JSONObject getHomePageInfo(HttpServletRequest request) {
        return Result.success(cpinfoService.getHomePageInfo(request));
    }

    @RequestMapping("/generateToken")
    @ResponseBody
    public JSONObject generateToken() {

        JSONObject token = cpinfoService.generateToken();
        if (token != null) {
            return Result.success(token);
        }

        return Result.fail("生成验证码错误");
    }

    @RequestMapping("/checkExistGoogle")
    @ResponseBody
    public JSONObject checkExistGoogle(String username) {

        boolean existGoogle = cpinfoService.checkExistGoogle(username);
        return Result.success(existGoogle);

    }

    /**
     * VVPay (v6) 公共谷歌密钥创建端点（登录前使用）
     */
    @RequestMapping("/createGoogleSecretPublic")
    @ResponseBody
    public JSONObject createGoogleSecretPublic(@RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        String username = json.getString("username");
        String password = json.getString("password");
        String token = json.getString("token");
        String inputVerifyCode = json.getString("inputVerifyCode");

        // 校验验证码
        if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(inputVerifyCode)) {
            if (!cpinfoService.checkVerifyCode(token, inputVerifyCode)) {
                return Result.fail("验证码错误");
            }
        }

        // 校验用户名密码
        CpinfoReqDTO reqDTO = new CpinfoReqDTO();
        reqDTO.setUsername(username);
        reqDTO.setPassword(password);
        JSONObject result = cpinfoService.createCpInfoSecret(reqDTO);
        if (result == null) {
            return Result.fail("用户名或密码错误");
        }
        return Result.success(result);
    }

}
