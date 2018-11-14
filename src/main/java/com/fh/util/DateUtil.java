package com.fh.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfOneYue = new SimpleDateFormat("M");

	private final static SimpleDateFormat sdfYue = new SimpleDateFormat("yyyy-MM");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDay1 = new SimpleDateFormat("MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmssSSS");


	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}
	/**
	 * yyyyMMddHHmmssSSS
	 *
	 * @return
	 */
	public static String getfile() {
		return sdfTimes.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 转换成YYYY-MM-DD格式
	 */
	public static String getDay2(Date data) {
		return sdfDay.format(data);
	}

	/**
	 * 转换成MM-DD格式
	 */
	public static String getDay3() {
		return sdfDay1.format(new Date());
	}

	/**
	 * 获取YYYY-MM格式
	 * 
	 * @return
	 */
	public static String getYue() {
		return sdfYue.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}
	/**
	 * 获取MM mm格式
	 *
	 * @return
	 */
	public static String getOneYue() {
		return sdfOneYue.format(new Date());
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;

		/*
		 * view.loadUrl(
		 * "javascript:function setTop(){document.getElementById('saveBtn').style.display=\"none\";}setTop();"
		 * ); view.loadUrl(
		 * "javascript:function setTop1(){document.getElementsByClassName('smart-header')[2].style.display=\"none\";}setTop1();"
		 * ); view.loadUrl(
		 * "javascript:function setTop12(){document.getElementById('div-gpt-ad-1466480176223-0').style.display=\"none\";}setTop12();"
		 * );
		 */
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author
	 *             luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String Datefirnatter(String DATE) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(DATE);
		java.text.SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		String date1 = formatter.format(date);// 格式化数据
		return date1;
	}

	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static String getTimeStr1(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = "";
		try {
			Date date = format.parse(time);
			Date date2 = new Date();

			long delta = (date2.getTime() - date.getTime()) / 1000;

			if (delta < 60) { // 1分钟内
				timeStr = "刚刚";
			} else if (delta < 60 * 60) { // 1小时内
				timeStr = delta / 60 + "分钟前";
			} else if (delta < 60 * 60 * 24) { // 1天内
				timeStr = delta / 60 / 60 + "小时前";
			} else {
				timeStr = time;
			}
			return timeStr.split(" ")[0];
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}

	public static String getDatePoor(Date endDate, Date nowDate) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	// 返回分钟
	public static Long TimeDiff(String pBeginTime, String pEndTime) throws Exception {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = dfs.parse(pBeginTime);
		Date end = dfs.parse(pEndTime);
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long min = between / 60;
		return min;
	}

	// 返回小时
	public static Long TimeDiff1(String pBeginTime, String pEndTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Long beginL = format.parse(pBeginTime).getTime();
		Long endL = format.parse(pEndTime).getTime();
		Long day = (endL - beginL) / 86400000;
		Long hour = ((endL - beginL) % 86400000) / 3600000;
		Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
		/* return ("实际请假" + day + "小时" + hour + "分钟" + min); */
		return hour;
	}

	// 返回天
	public static Long TimeDiff2(String pBeginTime, String pEndTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Long beginL = format.parse(pBeginTime).getTime();
		Long endL = format.parse(pEndTime).getTime();
		Long day = (endL - beginL) / 86400000;
		Long hour = ((endL - beginL) % 86400000) / 3600000;
		Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
		/* return ("实际请假" + day + "小时" + hour + "分钟" + min); */
		return day;
	}

	public static String times(String time) {
		String times = "";
		try {
			SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// @SuppressWarnings("unused")
			// long lcc = Long.valueOf(time);

			int i = Integer.parseInt(time);
			times = sdr.format(new Date(i * 1000L));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return times;

	}

	// 获取前一天
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	// 获取本周一
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}
	
	
	
	
	//获取本月第一天和最后一天
	public static Map<String,Object> ThisMonth(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        
        //获取前月的第一天
        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String firstDay = format.format(cal_1.getTime());

        
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();   
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
        String lastDay = format.format(cale.getTime());

        
        
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());

        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());

        Map<String,Object> map=new HashMap();
        map.put("firstDay", firstDay+" 00:00:00");
        map.put("lastDay", lastDay+" 00:00:00");
        map.put("first", first+" 00:00:00");
        map.put("last", last+" 00:00:00");
		return map;
	}
	
	//获取上周周一周日
	public static Map<String,Object> ThisZhou(){
		Calendar cal = Calendar.getInstance();  
        cal.setTime(getThisWeekMonday(new Date()));  
        cal.add(Calendar.DATE, -7);  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.err.println(df.format(cal.getTime()));
		
        Calendar cal1 =Calendar.getInstance();

		cal1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		cal1.add(Calendar.WEEK_OF_YEAR, 1);
	  
	   cal1.add(Calendar.DATE, -7);  
	   Map<String,Object> map=new HashMap();
	   map.put("firstZhou", df.format(cal.getTime())+" 00:00:00");
	   map.put("lastZhou", df.format(cal1.getTime())+" 00:00:00");
	   return map;
	}
	
	
	
	
	public static void aa(String json2){
		List<Object> lists=new ArrayList();
		List<PageData> list=new ArrayList();	//总数据源
		List<PageData> list3=new ArrayList();	//前三个
		for(int i=0;i<3;i++){
			list3.add(list.get(i));
		}
		lists.add(list3);
		for(int i=2;i<list.size();i++){
			if(list.get(i).getString("DSTATUS").equals("0")){
				lists.add(list.get(i));
			}else{
				List<PageData> listsss=new ArrayList();
				listsss.add(list.get(i));
				listsss.add(list.get(i+1));
				i++;
				lists.add(listsss);
			}
		}
		
		/*List<Object> lists=new ArrayList();
			List<PageData> list3=new ArrayList();	//前三个
			for(int i=0;i<3;i++){
				list3.add(list.get(i));
			}
			lists.add(list3);
			for(int i=3;i<list.size();i++){
				if(list.get(i).getString("DSTATUS").equals("1")){
					List<PageData> listss=new ArrayList();
					listss.add(list.get(i));
					listss.add(list.get(i+1));
					i++;
					lists.add(listss);
				}else{
					List<PageData> list2=new ArrayList();
					list2.add(list.get(i));
					lists.add(list2);
				}
			}
			List<PageData> list1=(List<PageData>) lists.get(1);
			System.err.println(list1.get(0).getString("IMG"));
			System.err.println(lists.size());*/
	}
	
	public static void main(String[] args) throws Exception {
		/*Date a = new Date();
		Thread.sleep(3000);Date b = new Date();
		long interval = (b.getTime() - a.getTime())/1000;
		System.out.println("两个时间相差"+interval+"秒");*/

		String dateStr = "2018-1-1 1:21:25";
		String dateStr2 = "2018-1-1 1:21:27";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);
			long interval = (date2.getTime() - date.getTime())/1000;
			System.out.println(interval);
			//System.out.println("两个日期的差距：" + differentDays(date,date2));
			//System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static int differentDaysByMillisecond(Date date1,Date date2)
	{
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}
	
	
	
	
}
