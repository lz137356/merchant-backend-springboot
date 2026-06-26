package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.config.PlatformProperties;
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

    @Autowired
    private PlatformProperties platformProperties;


    @RequestMapping("/queryMoneyLogPageList")
    public JSONObject queryMoneyLogPageList(HttpServletRequest request, MoneyLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));
        List<MoneyLogVO> moneyLogVoList = moneyLogService.queryMoneyLogPageList(reqDTO);
        int count = moneyLogService.queryMoneyLogPageListCount(reqDTO);
        return Result.success(moneyLogVoList, count);
    }


    @RequestMapping("/export")
    public void exportMoneyLog(HttpServletRequest request, HttpServletResponse response, MoneyLogReqDTO reqDTO)
            throws Exception {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            int code = platformProperties.isV6() ? 40100 : 444;
            JSONObject json = new JSONObject();
            json.put("code", code);
            json.put("msg", "login error");
            response.getWriter().print(json);
            return;
        }

        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));
        HSSFWorkbook sheets = moneyLogService.exportExcelMoneyLogList(reqDTO);
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String("MoneyFlowExport".getBytes("GB2312"), "8859_1") + ".xls");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
            outputStream.flush();
        }
    }
}
