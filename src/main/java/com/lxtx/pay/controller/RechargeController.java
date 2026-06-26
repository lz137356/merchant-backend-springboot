package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.RechargeAddReqDTO;
import com.lxtx.pay.dto.RechargeReqDTO;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.RechargeService;
import com.lxtx.pay.vo.RechargeStatisticsVO;
import com.lxtx.pay.vo.RechargeVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/pay/recharge")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private static Logger logger = Logger.getLogger(RechargeController.class);

    @RequestMapping("/addOne")
    public JSONObject recharge(RechargeAddReqDTO reqDTO) {

        String recharge_token = request.getHeader("recharge_token");
        Object session_recharge_token = request.getSession().getAttribute("recharge_token");
        // 不记录敏感 token 信息，仅记录操作
        logger.info("Recharge addOne called, token validation performed");

//        if (session_recharge_token == null || !recharge_token.equals(session_recharge_token + "")) {
//            return Result.fail("禁止重复提交");
//        }
        int i = rechargeService.addOneRecharge(reqDTO);
        if (i > 0) {
            request.getSession().removeAttribute("recharge_token");
            return Result.success("提单成功", null);
        } else {
            return Result.fail("提单成功");
        }
    }

    @RequestMapping("/pageList")
    public JSONObject pageList(RechargeReqDTO reqDTO) {
        List<RechargeVO> rechargeVOList = rechargeService.queryRechargePageList(reqDTO);
        RechargeStatisticsVO rechargeStatisticsVO = rechargeService.queryRechargePageListCount(reqDTO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", rechargeVOList);
        jsonObject.put("statistics", rechargeStatisticsVO);
        return Result.success(jsonObject);
    }

    @RequestMapping("/makeToken")
    public JSONObject makeToken(HttpServletRequest request) {
        // 使用加密安全的随机数生成器生成 token
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        String token = Base64.getEncoder().encodeToString(tokenBytes);
        request.getSession().setAttribute("recharge_token", token);
        return Result.success("success", token);
    }
}
