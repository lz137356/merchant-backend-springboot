package com.lxtx.pay.service.Impl;

import com.lxtx.pay.dto.MoneyLogReqDTO;
import com.lxtx.pay.handler.MoneylogHandler;
import com.lxtx.pay.service.MoneyLogService;
import com.lxtx.pay.utils.PageUtils;
import com.lxtx.pay.vo.MoneyLogExportVO;
import com.lxtx.pay.vo.MoneyLogVO;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class MoneyLogServiceImpl implements MoneyLogService {

    @Autowired
    private MoneylogHandler moneylogHandler;

    @Override
    public List<MoneyLogVO> queryMoneyLogPageList(MoneyLogReqDTO reqDTO) {

        if (StringUtils.isNotEmpty(reqDTO.getMoney())) {
            reqDTO.setMoney(new BigDecimal(reqDTO.getMoney()).multiply(new BigDecimal(100)).toString());
        }

        reqDTO.setRowIndex(PageUtils.getRowIndex(reqDTO.getPage(), reqDTO.getLimit()));

        List<MoneyLogVO> moneyLogVOList = moneylogHandler.queryMoneyLogPageList(reqDTO);
        for (MoneyLogVO m :
                moneyLogVOList) {
            Integer type = m.getType();
            Integer sceneInfo = m.getSceneInfo();
            try {
                if (org.apache.commons.lang3.StringUtils.isEmpty(m.getOrderId())) {
                    m.setOrderId(m.getNotes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (type == 1 && sceneInfo == 1) {
                m.setDetails("商户充值成功，余额增加。");
            } else if (type == 1 && sceneInfo == 3) {
                m.setDetails("商户提现失败，订单金额返回商户余额，余额增加。");
            } else if (type == 2 && sceneInfo == 3) {
                m.setDetails("商户进行提现，商户余额扣除订单金额，余额减少。");
            }
        }
        return moneyLogVOList;
    }

    @Override
    public int queryMoneyLogPageListCount(MoneyLogReqDTO reqDTO) {
        if (StringUtils.isNotEmpty(reqDTO.getMoney())) {
            reqDTO.setMoney(new BigDecimal(reqDTO.getMoney()).multiply(new BigDecimal(100)).toString());
        }
        return moneylogHandler.queryMoneyLogPageListCount(reqDTO);
    }

    @Override
    public void exportZipMoneyList(MoneyLogReqDTO reqDTO, HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/zip");
        response.setCharacterEncoding("UTF-8");
        String filename = "MONEY_FLOW_EXPORT_" + System.currentTimeMillis() + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
             OutputStreamWriter writer = new OutputStreamWriter(zipOut, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(writer,
                     CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.DEFAULT_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            // 创建ZIP文件条目
            String csvFilename = "money_flow_" + System.currentTimeMillis() + ".csv";
            ZipEntry zipEntry = new ZipEntry(csvFilename);
            zipEntry.setComment("资金流水导出数据");
            zipOut.putNextEntry(zipEntry);

            // 写入UTF-8 BOM到ZIP文件
            writer.write('\ufeff');

            // 写入CSV表头
            String[] header = {"Id", "商户id", "入金/出金", "交易类型", "交易前金额(元)", "交易金额(元)", "交易后金额(元)", "关联单号", "记账时间"};
            csvWriter.writeNext(header);

            // 分批查询和处理数据
            int batchSize = 5000;
            int currentPage = 0;
            int totalExported = 0;

            while (true) {
                // 设置分页参数
                reqDTO.setRowIndex(currentPage * batchSize);
                reqDTO.setLimit(batchSize);

                // 查询当前批次数据
                List<MoneyLogExportVO> batchList = moneylogHandler.exportExcelMoneyLogList(reqDTO);

                if (batchList == null || batchList.isEmpty()) {
                    break;
                }

                // 处理并写入当前批次数据
                for (MoneyLogExportVO m : batchList) {
                    String[] record = new String[]{
                            m.getId() != null ? m.getId().toString() : "",
                            m.getAppId() != null ? m.getAppId().toString() : "",
                            convertType(m.getType()),
                            convertSceneInfo(m.getSceneInfo(), m.getType()),
                            formatMoney(m.getFrontMoney()),
                            formatMoney(m.getMoney()),
                            formatMoney(m.getQueenMoney()),
                            m.getOrderId() != null ? m.getOrderId() : "",
                            m.getCreateTime() != null ? m.getCreateTime() : ""
                    };

                    csvWriter.writeNext(record);
                    totalExported++;
                }

                // 刷新缓冲区
                csvWriter.flush();

                // 判断是否还有更多数据
                if (batchList.size() < batchSize) {
                    break;
                }

                currentPage++;

                // 记录日志
                if (currentPage % 10 == 0) {
                    log.info("已导出数据批次: {}, 总条数: {}", currentPage + 1, totalExported);
                }
            }

            // 关闭ZIP条目
            zipOut.closeEntry();

            // 可选：添加一个说明文件到ZIP
            addReadmeToZip(zipOut, totalExported);

            log.info("ZIP导出完成，总导出条数: {}", totalExported);

        } catch (Exception e) {
            log.error("导出ZIP资金流水失败", e);
            throw new IOException("导出失败", e);
        }
    }

    // 添加说明文件到ZIP
    private void addReadmeToZip(ZipOutputStream zipOut, int totalRecords) throws IOException {
        ZipEntry readmeEntry = new ZipEntry("README.txt");
        zipOut.putNextEntry(readmeEntry);

        try (OutputStreamWriter writer = new OutputStreamWriter(zipOut, StandardCharsets.UTF_8)) {
            String readmeContent = "资金流水导出说明\n" +
                    "===================\n" +
                    "导出时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n" +
                    "总记录数: " + totalRecords + "\n" +
                    "文件编码: UTF-8\n" +
                    "列说明:\n" +
                    "1. Id: 记录ID\n" +
                    "2. 商户id: 商户标识\n" +
                    "3. 入金/出金: 增加/减少\n" +
                    "4. 交易类型: 代收/调账/代付成功/代付退回\n" +
                    "5. 交易前金额(元): 交易前账户余额\n" +
                    "6. 交易金额(元): 本次交易金额\n" +
                    "7. 交易后金额(元): 交易后账户余额\n" +
                    "8. 关联单号: 关联的业务订单号\n" +
                    "9. 记账时间: 交易发生时间\n";

            writer.write(readmeContent);
            writer.flush();
        }

        zipOut.closeEntry();
    }

    // 类型转换方法
    private String convertType(String type) {
        if (type == null) return "";
        return "1".equals(type) ? "增加" : "减少";
    }

    // 场景信息转换方法
    private String convertSceneInfo(String sceneInfo, String type) {
        if (sceneInfo == null) return "其他";

        switch (sceneInfo) {
            case "1":
                return "代收";
            case "2":
                return "调账";
            case "3":
                if ("1".equals(type)) {
                    return "代付退回";
                } else if ("2".equals(type)) {
                    return "代付成功";
                } else {
                    return "代付";
                }
            default:
                return "其他";
        }
    }

    // 金额格式化（分转元）
    private String formatMoney(Long money) {
        if (money == null || money == 0L) return "0.00";
        try {
            return new BigDecimal(money)
                    .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)
                    .toString();
        } catch (Exception e) {
            log.error("金额格式化失败: {}", money, e);
            return "0.00";
        }
    }

    // 时间格式化
    private String formatDateTime(String dateTime) {
        if (dateTime == null) return "";
        try {
            // 尝试解析并标准化格式
            SimpleDateFormat[] possibleFormats = {
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
                    new SimpleDateFormat("yyyyMMddHHmmss"),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("yyyy/MM/dd")
            };

            for (SimpleDateFormat format : possibleFormats) {
                try {
                    Date date = format.parse(dateTime);
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                } catch (Exception e) {
                    // 继续尝试下一个格式
                    continue;
                }
            }
            return dateTime; // 如果都无法解析，返回原字符串
        } catch (Exception e) {
            log.warn("时间格式化失败: {}", dateTime, e);
            return dateTime;
        }
    }
}
