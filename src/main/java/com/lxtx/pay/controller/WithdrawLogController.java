package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.dto.WithdrawLogReqDTO;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Result;
import com.lxtx.pay.service.WithdrawLogService;
import com.lxtx.pay.vo.WithdrawLogStaticticsVO;
import com.lxtx.pay.vo.WithdrawLogVO;
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
@RequestMapping("/pay/withdrawlog")
public class WithdrawLogController {

    @Autowired
    private WithdrawLogService withdrawLogService;

    @RequestMapping("/queryWithdrawLogPageList")
    public JSONObject queryWithdrawLogPageList(HttpServletRequest request, WithdrawLogReqDTO reqDTO) {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 444);
            jsonObject.put("msg", "login error");
            return jsonObject;
        }
        reqDTO.setAppId(cpInfo.getAppId() + "");

        List<WithdrawLogVO> withdrawLogVoList = withdrawLogService.queryWithdrawLogPageList(reqDTO);
        WithdrawLogStaticticsVO withdrawLogStaticticsVO = withdrawLogService.queryWithdrawLogPageListStatictics(reqDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", withdrawLogVoList);
        jsonObject.put("statictics", withdrawLogStaticticsVO);
        jsonObject.put("statistics", withdrawLogStaticticsVO);
        return Result.success(jsonObject);
    }

    @RequestMapping("/exportWithdrawLog")
    public void exportWithdrawLog(HttpServletRequest request, HttpServletResponse response, WithdrawLogReqDTO reqDTO) throws NoSuchFieldException, IllegalAccessException, IOException {
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        reqDTO.setAppId(String.valueOf(cpInfo.getAppId()));

        HSSFWorkbook sheets = withdrawLogService.exportExcelWithdrawLogList(reqDTO);
        OutputStream outputStream = response.getOutputStream();

        response.reset();
        // 设定输出文件头
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String("WithdrawExport".getBytes("GB2312"), "8859_1") + ".xls");
        // 定义输出类型
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        sheets.write(outputStream);

    }

}
