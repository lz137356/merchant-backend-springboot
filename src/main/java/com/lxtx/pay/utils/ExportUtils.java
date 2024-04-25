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


    public static  <T> HSSFWorkbook exportExcel(List<T> data, Class<T> clz) throws NoSuchFieldException, IllegalAccessException {

        Field[] fields = clz.getDeclaredFields();
        List<String> headers = new LinkedList<>();
        List<String> variables = new LinkedList<>();

        // 创建工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
        // 创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        // 创建表头
        Row rowHeader = sheet.createRow(0);

        // 表头处理
        for (int h = 0; h < fields.length; h++) {
            Field field = fields[h];
            if (field.isAnnotationPresent(ExcelHeader.class)) {
                // 表头
                ExcelHeader annotation = field.getAnnotation(ExcelHeader.class);
                headers.add(annotation.value());
                rowHeader.createCell(h).setCellValue(annotation.value());

                // 字段
                variables.add(field.getName());
            }
        }

        // 数据处理
        for (int i = 0; i < data.size() ; i++) {

            //创建工作表的行(表头占用1行, 这里从第二行开始)
            HSSFRow row = sheet.createRow(i + 1);
            // 获取一行数据
            T t = data.get(i);
            Class<?> aClass = t.getClass();
            // 填充列数据
            for (int j = 0; j < variables.size(); j++) {

                Field declaredField = aClass.getDeclaredField(variables.get(j));
                declaredField.setAccessible(true);

                String key = declaredField.getName();
                Object value = declaredField.get(t);

                if (value!=null){
                    row.createCell(j).setCellValue(value.toString());
                }
            }
        }
        return workbook;
    }

}
