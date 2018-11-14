package com.fh.util;

import java.util.LinkedList;
import java.util.List;

/**
 * 字符串相关方法
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}
	/**
	 * 比较两个String数组去除重复的数据
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] arrContrast(String[] arr1, String[] arr2){
        List<String> list = new LinkedList<String>();
        for (String str : arr1) {                //处理第一个数组,list里面的值为1,2,3,4
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {      //如果第二个数组存在和第一个数组相同的值，就删除
            if(list.contains(str)){
                list.remove(str);
            }
        }
        String[] result = {};   //创建空数组
        return list.toArray(result);    //List to Array
    }
	
public static String get_StringNum(String aaa){
	
	String str = "";
	str=aaa.trim();
	String str2="";
	if(str != null && !"".equals(str)){
	for(int i=0;i<str.length();i++){
	if(str.charAt(i)>=48 && str.charAt(i)<=57){
	str2+=str.charAt(i);
	}
	}

	}
	System.out.println(str2);
	return str2;
	}

}
