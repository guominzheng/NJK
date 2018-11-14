package com.fh.test;

import com.fh.util.PageData;
import com.fh.util.SmsBao;
import org.junit.jupiter.api.Test;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    @Test
    public void excel() throws Exception {
        Connection conn = getConn();
        String sql = "select * from linshi";
        PreparedStatement pstmt;
        List<PageData> pageList = null;
        String[] a = {"daiyan", "name", "address", "phone", "views","painum"};
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println(col);
            pageList = new ArrayList<PageData>();
            PageData pd = null;
            while (rs.next()) {
                pd = new PageData();
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    pd.put(a[i - 1], rs.getString(i));
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                pageList.add(pd);
                System.out.println("");
            }
        } catch (Exception e) {

        }
        System.out.println(pageList.size());
        int i = pageList.size();
        String[] b = {"代言词", "姓名", "地址", "电话", "投票数","投票排行"};
        File xlsFile = new File("C:\\Users\\xshel\\draw.xls");
        // 创建一个工作簿
        WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
        // 创建一个工作表
        WritableSheet sheet = workbook.createSheet("sheet1", 0);
        for(int c=0;c<6;c++){
            sheet.addCell(new Label(c, 0, b[c]));
        }
        for (int row = 1; row <= i; row++) {
            for (int col = 0; col < 6; col++) {
                // 向工作表中添加数据
                    sheet.addCell(new Label(col, row, pageList.get(row-1).getString(a[col])));
            }
        }
        workbook.write();
        workbook.close();
    }

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://115.28.210.33:3306/njk";
        String username = "root";
        String password = "xing123456";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    @Test
    public void sendSms(){
        Connection conn = getConn();
        SmsBao smsBao = new SmsBao();
        String sql = "SELECT\n" +
                "\tUSERNAME\n" +
                "FROM\n" +
                "\tsys_app_user\n" +
                "WHERE\n" +
                "\tVIP = '1'\n" +
                "OR VIP = '3'";
        PreparedStatement pstmt;
        List<PageData> pageList = null;
        String[] a = {"USERNAME"};
        try{
            pstmt =  conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            pageList = new ArrayList<PageData>();
            PageData pd = null;
            while (rs.next()) {
                pd = new PageData();
                for (int i = 1; i <= col; i++) {
                    pd.put(a[i-1],rs.getString(i));
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                pageList.add(pd);
            }
            int count=0;
        System.out.println(pageList.size());
            for(int i=0;i<pageList.size();i++){
                count++;
                System.out.println("执行了=========="+count+"次.");
                smsBao.sendSMS(pageList.get(i).getString("USERNAME"),"“发钱了！”\n" +
                        "尊敬的农极客VIP用户，为了感谢猫友一直以来对农极客的支持，“国庆代金券大放送”，现在赠送您一张50元代金券！\n" +
                        "该代金券，农极客商城所有产品通用，无门槛，有效期6个月，想啥时候用就啥时候用~\n" +
                        "点击链接 http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk 即可进入农极客APP查看详情。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void  tset(){
        SmsBao smsBao = new SmsBao();
        try {
            smsBao.sendSMS("17637926780","【农极客】“发钱了！”\n" +
                    "尊敬的农极客VIP用户，为了感谢猫友一直以来对农极客的支持，“国庆代金券大放送”，现在赠送您一张50元代金券！\n" +
                    "该代金券，农极客商城所有产品通用，无门槛，有效期6个月，想啥时候用就啥时候用~\n" +
                    "点击链接 http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk 即可进入农极客APP查看详情。");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}