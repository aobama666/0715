package com.guli.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;

public class ReaderTest {

    /**
     * 03版本读取数据
     */
    @Test
    public void reader03Test() throws Exception{
        //1、读取excl文件 流
        FileInputStream inputStream = new FileInputStream("D:/test-write03.xls");
        //2、创建WorkBook对象：根据流来创建的
        Workbook workbook = new HSSFWorkbook(inputStream);
        //3、获取Sheet
        Sheet sheetAt = workbook.getSheetAt(0);
        //4、获取一行
        Row row = sheetAt.getRow(1);
        //5、获取行中的列
        Cell cell = row.getCell(0);
        //6、获取列数据
        String value = cell.getStringCellValue();
        //7、打印
        System.err.println("id:"+value);
        //8、关闭流
        inputStream.close();
    }

    @Test
    public void reader07Test() throws Exception{
        //1、读取excl文件 流
        FileInputStream inputStream = new FileInputStream("D:/test-write07.xlsx");
        //2、创建WorkBook对象：根据流来创建的
        Workbook workbook = new XSSFWorkbook(inputStream);
        //3、获取Sheet
        Sheet sheetAt = workbook.getSheetAt(0);
        //4、获取一行
        Row row = sheetAt.getRow(1);
        //5、获取行中的列
        Cell cell = row.getCell(1);
        //6、获取列数据
        String value = cell.getStringCellValue();
        //7、打印
        System.err.println("name:"+value);
        //8、关闭流
        inputStream.close();
    }
}
