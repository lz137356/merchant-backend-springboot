package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.MoneyLogService;
import com.lxtx.pay.vo.MoneyLogVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
    public void exportPayLog(HttpServletRequest request, HttpServletResponse response, MoneyLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException, IOException, IOException {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = moneyLogService.exportExcelMoneyList(reqDTO);
        OutputStream outputStream = response.getOutputStream();

        response.reset();
        // 设定输出文件头
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String("MoneyExport".getBytes("GB2312"), "8859_1") + ".xls");
        // 定义输出类型
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        sheets.write(outputStream);
    }
}
