package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/createGoogleSecret")
    @ResponseBody
    public JSONObject createGoogleSecret(HttpServletRequest request) {
        CpInfoSettingVO cpInfoSecret = cpinfoService.createCpInfoSecret(request);
        if (cpInfoSecret != null) {
            return Result.success(cpInfoSecret);
        }
        return Result.fail("生成谷歌秘钥失败");
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response, CpinfoReqDTO reqDTO) throws IOException {
        int login = cpinfoService.login(request, reqDTO);


        if (login > 0) {
            return Result.success("登陆成功", request.getSession().getId());
        } else if (login == -2) {
            return Result.fail("谷歌验证码验证失败");
        }
        else {
            return Result.fail("登陆失败");
        }
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
            return Result.success("true",null);
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


    @RequestMapping("/getGoogleSecret")
    @ResponseBody
    public JSONObject getGoogleSecret(HttpServletRequest request) {
        JSONObject googleSecret = cpinfoService.getGoogleSecret(request);

        return Result.success(googleSecret);
    }

}
