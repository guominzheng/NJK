package com.fh.util;

/**
 * @ClassName: GetMobileMessage
 * @Description: TODO
 * @date 2016年1月28日 下午2:40:56
 */
public class GetMobileMessage {
    private static final String PHONE_PLACE_API_URL = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";

    /**
     * @param @param  mobile
     * @param @return
     * @return String
     * @throws
     * @Title: getMobilePlace
     * @Description: 获取手机归属地信息
     */
    public static String getMobilePlace(String mobile) {
        HttpClientUtil util = new HttpClientUtil();
        try {
            //访问拍拍的查询接口
            String mobileMessage = util.getWebcontent(PHONE_PLACE_API_URL + mobile, "GB2312");
            String[] stringss = mobileMessage.split(",");
           /* System.out.println(mobileMessage);*/
            //（页面获取到的消息，除了这些，还有一些html语句）
            String address = stringss[1].split(":")[1].replaceAll("['\"<>#&]", "");
           // System.out.println("---------------------->" + address);
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println(GetMobileMessage.getMobilePlace("18736040966"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}