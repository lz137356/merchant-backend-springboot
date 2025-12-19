package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.MoneyLogService;
import com.lxtx.pay.vo.MoneyLogVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pay/moneylog")
public class MoneyLogController {

    @Autowired
    private MoneyLogService moneyLogService;


    @RequestMapping("/queryMoneyLogPageList")
    public JSONObject queryMoneyLogPageList(HttpServletRequest request, MoneyLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));
        List<MoneyLogVO> moneyLogVoList = moneyLogService.queryMoneyLogPageList(reqDTO);
        int count = moneyLogService.queryMoneyLogPageListCount(reqDTO);
        return Result.success(moneyLogVoList, count);
    }


    @RequestMapping("/export")
    public void exportMoneyLogZip(HttpServletRequest request, HttpServletResponse response, MoneyLogReqDTO reqDTO)
            throws IOException {

        try {
            // 获取商户信息
            CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
            if (cpInfo == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("未授权访问");
                return;
            }

            reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

            // 使用ZIP导出
            moneyLogService.exportZipMoneyList(reqDTO, response);

        } catch (Exception e) {
            log.error("导出ZIP资金流水失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("导出失败：" + e.getMessage());
        }
    }
}
