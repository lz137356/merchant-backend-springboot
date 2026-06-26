package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.PayLogMatchUtrReqDTO;
import com.lxtx.pay.dto.PayLogReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.PayLogService;
import com.lxtx.pay.vo.PayLogStaticticsVO;
import com.lxtx.pay.vo.PayLogVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/pay/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @RequestMapping("/sync")
    public JSONObject sync(HttpServletRequest request, PayLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId() + "");

        int i = payLogService.changeSyncCnt(reqDTO);

        if (i > 0) {
            return Result.success("已初始化通知状态，等待系统自动通知");
        }

        return Result.fail("补发失败");
    }

    @RequestMapping("/queryPayLogPageList")
    public JSONObject queryPayLogPageList(HttpServletRequest request, PayLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId() + "");
        List<PayLogVo> payLogVoList = payLogService.queryPayLogPageList(reqDTO);
        PayLogStaticticsVO payLogStaticticsVO = payLogService.queryPayLogPageListStatictics(reqDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", payLogVoList);
        jsonObject.put("statictics", payLogStaticticsVO);
        jsonObject.put("statistics", payLogStaticticsVO);
        return Result.success(jsonObject);
    }

    @RequestMapping("/exportPayLog")
    public void exportPayLog(HttpServletRequest request, HttpServletResponse response, PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException, IOException {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = payLogService.exportExcelPayLogList(reqDTO);
        OutputStream outputStream = response.getOutputStream();

        response.reset();
        // 设定输出文件头
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String("RechargeExport".getBytes("GB2312"), "8859_1") + ".xls");
        // 定义输出类型
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        sheets.write(outputStream);

    }

    @RequestMapping("/queryPayLogHistoryPageList")
    public JSONObject queryPayLogHistoryPageList(HttpServletRequest request, PayLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(cpInfo.getAppId() + "");
        List<PayLogVo> payLogVoList = payLogService.queryPayLogHistoryPageList(reqDTO);
        PayLogStaticticsVO payLogStaticticsVO = payLogService.queryPayLogHistoryPageListStatictics(reqDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", payLogVoList);
        jsonObject.put("statictics", payLogStaticticsVO);
        jsonObject.put("statistics", payLogStaticticsVO);
        return Result.success(jsonObject);
    }

    @RequestMapping("/exportPayLogHistory")
    public void exportPayLogHistory(HttpServletRequest request, HttpServletResponse response, PayLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException, IOException {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = payLogService.exportExcelPayLogHistoryList(reqDTO);
        OutputStream outputStream = response.getOutputStream();

        response.reset();
        // 设定输出文件头
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String("RechargeExport".getBytes("GB2312"), "8859_1") + ".xls");
        // 定义输出类型
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        sheets.write(outputStream);

    }

}
