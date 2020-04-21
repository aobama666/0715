package com.guli.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;

public class WriterTest {

    /**
     * 03版本写入的Excel
     */
    @Test
    public void writer03Test()throws  Exception{

        //怎么写到Excl表格中
        //1、创建一个WorkBook这个工作表
        Workbook workbook = new HSSFWorkbook();
        //2、在这个工作表中创建Sheet
        Sheet sheet = workbook.createSheet("订单管理");
        //3、在这个Sheet中创建一行
        //行和列索引值都是从0开始
        Row row_0 = sheet.createRow(0);
        //4、在行中创建一列
        Cell cell_0_0 = row_0.createCell(0);
        //5、在列中设置数据
        cell_0_0.setCellValue("id");
        Cell cell_0_1 = row_0.createCell(1);
        //5、在列中设置数据
        cell_0_1.setCellValue("name");
        Cell cell_0_2 = row_0.createCell(2);
        //5、在列中设置数据
        cell_0_2.setCellValue("age");

        //设置第二行的数据
        Row row_1 = sheet.createRow(1);
        Cell cell_1_0 = row_1.createCell(0);
        cell_1_0.setCellValue("10001");
        Cell cell_1_1 = row_1.createCell(1);
        cell_1_1.setCellValue("小四");
        Cell cell_1_2 = row_1.createCell(2);
        cell_1_2.setCellValue("16");

        //6、指定要输出到磁盘的文件路径
        FileOutputStream out = new FileOutputStream("d:/test-write03.xls");
        //7、把WorkBook输出到文件
        workbook.write(out);
        //8、关闭流
        out.close();
    }


    /**
     * 03版本写入的Excel
     */
    @Test
    public void writer07Test()throws  Exception{

        //怎么写到Excl表格中
        //1、创建一个WorkBook这个工作表
        Workbook workbook = new XSSFWorkbook();
        //2、在这个工作表中创建Sheet
        Sheet sheet = workbook.createSheet("订单管理");
        //3、在这个Sheet中创建一行
        //行和列索引值都是从0开始
        Row row_0 = sheet.createRow(0);
        //4、在行中创建一列
        Cell cell_0_0 = row_0.createCell(0);
        //5、在列中设置数据
        cell_0_0.setCellValue("id");
        Cell cell_0_1 = row_0.createCell(1);
        //5、在列中设置数据
        cell_0_1.setCellValue("name");
        Cell cell_0_2 = row_0.createCell(2);
        //5、在列中设置数据
        cell_0_2.setCellValue("age");

        //设置第二行的数据
        Row row_1 = sheet.createRow(1);
        Cell cell_1_0 = row_1.createCell(0);
        cell_1_0.setCellValue("10001");
        Cell cell_1_1 = row_1.createCell(1);
        cell_1_1.setCellValue("小四");
        Cell cell_1_2 = row_1.createCell(2);
        cell_1_2.setCellValue("16");

        //6、指定要输出到磁盘的文件路径
        FileOutputStream out = new FileOutputStream("d:/test-write07.xlsx");
        //7、把WorkBook输出到文件
        workbook.write(out);
        //8、关闭流
        out.close();
    }

    /**
     * 写03版本的HSSF最多65536行测试
     */
    @Test
    public void hssfTest()throws Exception{
        //记录开始时间
        long begin = System.currentTimeMillis();
        //1、创建一个WorkBook这个工作表
        Workbook workbook = new HSSFWorkbook();
        //2、在这个工作表中创建Sheet
        Sheet sheet = workbook.createSheet("订单管理");
        //3、在这个Sheet中创建一行
        //行和列索引值都是从0开始
        for(int i = 0; i < 65536; i++){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(i);
        }
        //6、指定要输出到磁盘的文件路径
        FileOutputStream out = new FileOutputStream("d:/hssf-write03.xls");
        //7、把WorkBook输出到文件
        workbook.write(out);
        //8、关闭流
        out.close();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000); // 5.371
    }

    @Test
    public void xssfTest()throws Exception{
        //记录开始时间
        long begin = System.currentTimeMillis();
        //1、创建一个WorkBook这个工作表
        Workbook workbook = new XSSFWorkbook();
        //2、在这个工作表中创建Sheet
        Sheet sheet = workbook.createSheet("订单管理");
        //3、在这个Sheet中创建一行
        //行和列索引值都是从0开始
        for(int i = 0; i < 65537; i++){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(i);
        }
        //6、指定要输出到磁盘的文件路径
        FileOutputStream out = new FileOutputStream("d:/xssf-write07.xlsx");
        //7、把WorkBook输出到文件
        workbook.write(out);
        //8、关闭流
        out.close();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000); // 3.144
    }

    @Test
    public void testWrite07BigDataFast()throws Exception{
        //记录开始时间
        long begin = System.currentTimeMillis();
        //1、创建一个WorkBook这个工作表
        Workbook workbook = new SXSSFWorkbook();
        //2、在这个工作表中创建Sheet
        Sheet sheet = workbook.createSheet("订单管理");
        //3、在这个Sheet中创建一行
        //行和列索引值都是从0开始
        for(int i = 0; i < 100000; i++){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(i);
        }
        //6、指定要输出到磁盘的文件路径
        FileOutputStream out = new FileOutputStream("d:/test-write07-bigdata-fast.xlsx");
        //7、把WorkBook输出到文件
        workbook.write(out);
        //8、关闭流
        out.close();

        //清除临时文件
        ((SXSSFWorkbook)workbook).dispose();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000); // 1.677
    }
}
