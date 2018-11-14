package com.fh.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XiaoYiDateT {
    /**
     * 获取当前天数的前七天
     *
     * @return
     */
    public static List<String> getDateList(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        List<String> tmList = new ArrayList<String>();
        int i;
        for (i = 7; i >=0; i--) {
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            tmList.add(sdf.format(date));
        }
        return tmList;
    }



    public static List<String> getYDateList(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> tmList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int i;
        for (i = 10; i >=0; i--) {
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,-1);
            date = calendar.getTime();
            tmList.add(sdf.format(date));
        }
        int m = calendar.get(Calendar.MONTH);
        return tmList;
    }


}
