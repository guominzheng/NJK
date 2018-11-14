package com.fh.util;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


public class FileUploadUtilClass {


    private static final ReentrantLock lock = new ReentrantLock();
    /**
     *
     * @param acct             当前MultipartFile对象
     * @param request          当前HttpServletRequest对象
     * @param fileSize         文件的既定大小
     * @param upPath           文件上传的路径
     * @param SysStaticPath    静态资源的系统url
     * @return      文件的访问路径 1:上传的文件为空
     */
    public static Map fileUploadImg(MultipartFile acct, HttpServletRequest request, int fileSize ,String upPath,String SysStaticPath,String upType) {
        Map <String,String> map =new HashMap();
        // 创建一个用于接收文件路径 的String对象
        String picture = "";
        String fileName="";
        // 创建上传路径
        String path = "";
        // 判断上传的文件是否为空
        if (!acct.isEmpty()) {
            String a = DateUtil.getDay().replaceAll("-","");
            upPath = upPath+"shengyin\\"+a+"\\";
            path = FileUploadUtilClass.getSystemPath(request,upPath);
            System.out.println(path);
            // 获取源文件后缀
            String prefix = FilenameUtils.getExtension(acct
                    .getOriginalFilename());
            if (acct.getSize() > fileSize) // 判断上传文件的大小是否与规定的大小匹配。
            {
                map.put("code","2");
                map.put("picture","文件大小不匹配");// 2：文件大小不匹配
            } else if (FileUploadUtilClass.getUpType(upType,prefix))//判断上传的文件后缀是否匹配
            {
                if("audio".equals(upType)){
                    prefix="mp3" ;
                }else if("img".equals(upType)){
                    prefix="jpg" ;
                }

                fileName = FileUploadUtilClass.getFileName(prefix); //获取新的文件命
                File targetFile = new File(path, fileName);//创建file
                //判断此文件路径是否存在 若不在 则创建
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 使用MultipartFile对象进行保存文件
                try {
                    acct.transferTo(targetFile);
                    upPath = upPath.replace("\\","/");
                    picture = SysStaticPath+upPath+fileName;
                    System.out.println(picture);
                    map.put("code","1");
                    map.put("picture",picture);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code","2");
                    map.put("picture","上传失败");// 4:为上传失败
                }
            } else {
                map.put("code","2");
                map.put("picture","文件后缀错误");// 3：文件后缀错误
            }
        } else {
            map.put("code","2");
            map.put("picture","上传的文件为空");// 1:上传的文件为空
        }
        return map;
    }



    public static Map nativeUpload(MultipartFile acct, HttpServletRequest request, int fileSize ,String upPath,String SysStaticPath) {
        Map <String,String> map =new HashMap();
        // 创建一个用于接收文件路径 的String对象
        String picture = "";
        String fileName="";
        // 创建上传路径
        String path = "";
        // 判断上传的文件是否为空
        if (!acct.isEmpty()) {
            String a = DateUtil.getDay().replaceAll("-","");
            upPath = upPath+"shengyin\\"+a+"\\";
            path = FileUploadUtilClass.getSystemPath(request,upPath);
            System.out.println(path);
            // 获取源文件后缀
            String prefix = FilenameUtils.getExtension(acct
                    .getOriginalFilename());
            if (acct.getSize() > fileSize) // 判断上传文件的大小是否与规定的大小匹配。
            {
                map.put("code","2");
                map.put("picture","文件大小不匹配");// 2：文件大小不匹配
            }
                fileName = FileUploadUtilClass.getFileName(prefix); //获取新的文件命
                File targetFile = new File(path, fileName);//创建file
                //判断此文件路径是否存在 若不在 则创建
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 使用MultipartFile对象进行保存文件
                try {
                    acct.transferTo(targetFile);
                    upPath = upPath.replace("\\","/");
                    picture = SysStaticPath+upPath+fileName;
                    System.out.println(picture);
                    map.put("code","1");
                    map.put("picture",picture);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code","2");
                    map.put("picture","上传失败");// 4:为上传失败
                }
        } else {
            map.put("code","2");
            map.put("picture","上传的文件为空");// 1:上传的文件为空
        }
        return map;
    }


    /**
     * 获取工程上传文件的路径
     *
     * @return String 上传路径
     */
    private static String getSystemPath(HttpServletRequest request,String upPath) {
        String path ="";
        path = request.getServletContext().getRealPath(
                "\\"+upPath);
        return path;
    }

    /**
     *
     * @param upType 上传的类型
     * @param prefix  上传文件的后缀
     * @return  boolean 匹配文件类型和后缀的结果
     */
    private static boolean getUpType(String upType,String prefix){
        boolean flag = false;
        if("image".equals(upType))
        {
           // flag = FileUploadUtilClass.epImage(prefix);
            flag = true;
        }
        else if("audio".equals(upType))
        {
            // flag = FileUploadUtilClass.epAudio(prefix);
            flag = true;
        }
        else if("video".equals(upType))
        {
            flag = FileUploadUtilClass.epVideo(prefix);
        }
        return flag;
    }
    /**
     * 文件上传图片过滤
     * 只提供过滤 jpg  png  jpeg pneg  四种格式
     *
     * @param image
     * @return true  false
     */
    private static boolean epImage(String image) {
        //做一个标记
        boolean flag = false;
        if ((image.equalsIgnoreCase("jpg")
                || image.equalsIgnoreCase("png")
                || image.equalsIgnoreCase("jpeg")
                || image.equalsIgnoreCase("pneg"))
                ) {
            flag = true;
        }
        return flag;
    }

    /**
     * 文件上传音频过滤
     * 只提供过滤 MP3  AMR  WMA WAVE  四种格式
     *
     * @param image
     * @return true  false
     */
    private static boolean epAudio(String image) {
        //做一个标记
        boolean flag = false;
        if ((image.equalsIgnoreCase("MP3")
                || image.equalsIgnoreCase("AMR")
                || image.equalsIgnoreCase("WMA")
                || image.equalsIgnoreCase("WAVE"))
                ) {
            flag = true;
        }
        return flag;
    }
    /**
     * 文件上传视频过滤
     * 只提供过滤 AVI  MP4  WMV RM  四种格式
     *
     * @param image
     * @return true  false
     */
    private static boolean epVideo(String image) {
        //做一个标记
        boolean flag = false;
        if ((image.equalsIgnoreCase("AVI")
                || image.equalsIgnoreCase("MP4")
                || image.equalsIgnoreCase("WMV")
                || image.equalsIgnoreCase("RM"))
                ) {
            flag = true;
        }
        return flag;
    }

    /**
     *  获取新文件名字，使用currentTimeMillis创建当前时间的long类型为避免并发的问题，使用了小粒度锁
     * @param prefix 文件后缀
     * @return
     */
    private static String getFileName(String prefix) {
        String fileName = "";
        try {
            lock.lock();
            fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_njk." + prefix;
        } finally {
            lock.unlock();
        }
        return fileName;
    }
}
