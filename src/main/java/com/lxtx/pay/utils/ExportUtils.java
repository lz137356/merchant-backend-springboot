package com.lxtx.pay.utils;

import com.lxtx.pay.annotations.ExcelHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ExportUtils {
    public static <T> HSSFWorkbook exportExcel(List<T> data, Class<T> clz) throws NoSuchFieldException, IllegalAccessException {
        final int MAX_ROWS_PER_SHEET = 65535; // HSSFWorkbook的行数限制

        Field[] fields = clz.getDeclaredFields();
        List<String> headers = new LinkedList<>();
        List<String> variables = new LinkedList<>();

        // 创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null;

        // 计算需要多少个工作表
        int sheetCount = (int) Math.ceil((double) data.size() / MAX_ROWS_PER_SHEET);

        // 表头处理
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelHeader.class)) {
                ExcelHeader annotation = field.getAnnotation(ExcelHeader.class);
                headers.add(annotation.value());
                variables.add(field.getName());
            }
        }

        for (int s = 0; s < sheetCount; s++) {
            // 创建工作表对象
            sheet = workbook.createSheet("Sheet" + (s + 1));

            // 创建表头
            Row rowHeader = sheet.createRow(0);
            for (int h = 0; h < headers.size(); h++) {
                rowHeader.createCell(h).setCellValue(headers.get(h));
            }

            // 数据处理
            for (int i = s * MAX_ROWS_PER_SHEET; i < (s + 1) * MAX_ROWS_PER_SHEET && i < data.size(); i++) {
                // 创建工作表的行(表头占用1行, 这里从第二行开始)
                Row row = sheet.createRow((i % MAX_ROWS_PER_SHEET) + 1);
                // 获取一行数据
                T t = data.get(i);
                Class<?> aClass = t.getClass();
                // 填充列数据
                for (int j = 0; j < variables.size(); j++) {
                    Field declaredField = aClass.getDeclaredField(variables.get(j));
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(t);
                    if (value != null) {
                        row.createCell(j).setCellValue(value.toString());
                    }
                }
            }
        }
        return workbook;
    }



}
