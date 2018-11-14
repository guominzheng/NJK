package com.fh.util.poi;

import com.fh.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.Region;

import java.io.FileOutputStream;
import java.io.IOException;

public class test {


    public static void aaa() throws IOException {
		//创建workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workbook.createSheet("学生表");
		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象?
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直?
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平?


		//创建单元格
		HSSFRow row = sheet.createRow(0);
		HSSFCell c0 = row.createCell(0);
		c0.setCellValue(new HSSFRichTextString("客户名称"));
		c0.setCellStyle(style);

		HSSFCell c1 = row.createCell(1);
		c1.setCellValue(new HSSFRichTextString("手机号"));
		c1.setCellStyle(style);

		HSSFCell c2 = row.createCell(2);
		c2.setCellValue(new HSSFRichTextString("商品明细"));
		c2.setCellStyle(style);

		HSSFCell c3 = row.createCell(9);
		c3.setCellValue(new HSSFRichTextString("合计金额"));
		c3.setCellStyle(style);

		HSSFCell c4 = row.createCell(10);
		c4.setCellValue(new HSSFRichTextString("结算方式"));
		c4.setCellStyle(style);

		HSSFCell c5 = row.createCell(11);
		c5.setCellValue(new HSSFRichTextString("实收"));
		c5.setCellStyle(style);

		HSSFCell c6 = row.createCell(12);
		c6.setCellValue(new HSSFRichTextString("优惠"));
		c6.setCellStyle(style);

		HSSFCell c7 = row.createCell(13);
		c7.setCellValue(new HSSFRichTextString("余欠"));
		c7.setCellStyle(style);

		HSSFCell c8 = row.createCell(14);
		c8.setCellValue(new HSSFRichTextString("开单人"));
		c8.setCellStyle(style);

		HSSFCell c9 = row.createCell(15);
		c9.setCellValue(new HSSFRichTextString("开单时间"));
		c9.setCellStyle(style);


		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cc1 = row1.createCell(2);
		cc1.setCellValue(new HSSFRichTextString("商品名称"));
		cc1.setCellStyle(style);

		HSSFCell cc2 = row1.createCell(3);
		cc2.setCellValue(new HSSFRichTextString("规格单位"));
		cc2.setCellStyle(style);

		HSSFCell cc3 = row1.createCell(4);
		cc3.setCellValue(new HSSFRichTextString("单价"));
		cc3.setCellStyle(style);

		HSSFCell cc4 = row1.createCell(5);
		cc4.setCellValue(new HSSFRichTextString("单价单位"));
		cc4.setCellStyle(style);

		HSSFCell cc5 = row1.createCell(6);
		cc5.setCellValue(new HSSFRichTextString("销售数量"));
		cc5.setCellStyle(style);

		HSSFCell cc6 = row1.createCell(7);
		cc6.setCellValue(new HSSFRichTextString("销售单位"));
		cc6.setCellStyle(style);

		HSSFCell cc7 = row1.createCell(8);
		cc7.setCellValue(new HSSFRichTextString("单品总价"));
		cc7.setCellStyle(style);

		// 四个参数分别是：起始行，起始列，结束行，结束列?
		Region region0 = new Region(0, (short)0, 1, (short)0);
		Region region1 = new Region(0, (short)1, 1, (short)1);
		Region region2 = new Region(0, (short)2, 0, (short)8);
		Region region3 = new Region(0, (short)9, 1, (short)9);
		Region region4 = new Region(0, (short)10, 1, (short)10);
		Region region5 = new Region(0, (short)11, 1, (short)11);
		Region region6 = new Region(0, (short)12, 1, (short)12);
		Region region7 = new Region(0, (short)13, 1, (short)13);
		Region region8 = new Region(0, (short)14, 1, (short)14);
		Region region9 = new Region(0, (short)15, 1, (short)15);
		sheet.addMergedRegion(region0);
		sheet.addMergedRegion(region1);
		sheet.addMergedRegion(region2);
		sheet.addMergedRegion(region3);
		sheet.addMergedRegion(region4);
		sheet.addMergedRegion(region5);
		sheet.addMergedRegion(region6);
		sheet.addMergedRegion(region7);
		sheet.addMergedRegion(region8);
		sheet.addMergedRegion(region9);




		int num= 2;
		for (int i=2;i<5;i++){
			HSSFRow rowss = sheet.createRow(i);
			HSSFCell cs2 = rowss.createCell(2);
			cs2.setCellValue(new HSSFRichTextString("邢宣聚"+i));
			cs2.setCellStyle(style);
		}

		HSSFRow rows = sheet.createRow(num);
		HSSFCell cs0 = rows.createCell(0);
		cs0.setCellValue(new HSSFRichTextString("邢宣聚"));
		cs0.setCellStyle(style);

		HSSFCell cs1 = rows.createCell(1);
		cs1.setCellValue(new HSSFRichTextString("18736083059"));
		cs1.setCellStyle(style);
		// 四个参数分别是：起始行，起始列，结束行，结束列?
		Region regions0 = new Region(2, (short)0, 4, (short)0);
		Region regions1 = new Region(2, (short)1, 4, (short)1);
		Region regions2 = new Region(num, (short)1, num, (short)1);

		sheet.addMergedRegion(regions0);
		sheet.addMergedRegion(regions1);






		sheet.addMergedRegion(regions2);
		for(int i=0;i<3;i++) {
                        HSSFRow rowss = sheet.createRow(num);
                        HSSFCell cs3 = rowss.createCell(2);
                        cs3.setCellValue(new HSSFRichTextString("欣喜" + i));
                        cs3.setCellStyle(style);

                        HSSFCell cs4 = rowss.createCell(3);
                        cs4.setCellValue(new HSSFRichTextString("1L" + i));
                        cs4.setCellStyle(style);
                        num++;
                    }



		FileOutputStream stream = new FileOutputStream("C://Users//xshel//Desktop//tests.xls");
		workbook.write(stream);
	}


    public static void main(String[] args) throws Exception{
	    System.out.println("NJK"+DateUtil.getfile()+".xls");
    }
}
