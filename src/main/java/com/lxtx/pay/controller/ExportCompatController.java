package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.config.PlatformProperties;
import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.dto.WithdrawLogReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.service.MoneyLogService;
import com.lxtx.pay.service.PayLogService;
import com.lxtx.pay.service.WithdrawLogService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出端点兼容层：支持 Velopay (v5) 和 VVPay (v6) 两种前端路径
 * - 支持 GET (v6) 和 POST (v5) 方法
 */
@RestController
public class ExportCompatController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private WithdrawLogService withdrawLogService;

    @Autowired
    private MoneyLogService moneyLogService;

    @Autowired
    private PlatformProperties platformProperties;

    @RequestMapping(value = "/paylog/exportPayLog", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportPayLog(HttpServletRequest request, HttpServletResponse response, PayLogReqDTO reqDTO)
            throws Exception {
        CpInfo cpInfo = requireSession(request, response);
        if (cpInfo == null) {
            return;
        }
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = payLogService.exportExcelPayLogList(reqDTO);
        writeXls(response, sheets, "RechargeExport");
    }

    @RequestMapping(value = "/withdrawlog/exportWithdrawLog", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportWithdrawLog(HttpServletRequest request, HttpServletResponse response, WithdrawLogReqDTO reqDTO)
            throws Exception {
        CpInfo cpInfo = requireSession(request, response);
        if (cpInfo == null) {
            return;
        }
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = withdrawLogService.exportExcelWithdrawLogList(reqDTO);
        writeXls(response, sheets, "WithdrawExport");
    }

    @RequestMapping(value = "/moneylog/export", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportMoneyLog(HttpServletRequest request, HttpServletResponse response, MoneyLogReqDTO reqDTO)
            throws Exception {
        CpInfo cpInfo = requireSession(request, response);
        if (cpInfo == null) {
            return;
        }
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = moneyLogService.exportExcelMoneyLogList(reqDTO);
        writeXls(response, sheets, "MoneyFlowExport");
    }

    private CpInfo requireSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            int code = platformProperties.isV6() ? 40100 : 444;
            JSONObject json = new JSONObject();
            json.put("code", code);
            json.put("msg", "login error");
            response.getWriter().print(json);
        }
        return cpInfo;
    }

    private static void writeXls(HttpServletResponse response, HSSFWorkbook sheets, String baseName) throws IOException {
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String(baseName.getBytes("GB2312"), "8859_1") + ".xls");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
            outputStream.flush();
        }
    }
}
