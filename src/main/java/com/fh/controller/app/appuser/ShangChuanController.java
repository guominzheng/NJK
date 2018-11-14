package com.fh.controller.app.appuser;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.base.BaseController;
import com.fh.entity.vo.LiveRecord;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUploadUtilClass;
import com.fh.util.PathUtil;
import com.fh.util.jdeis.JedisClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

 */
@Controller
@RequestMapping(value = "app/shangchuan")
public class ShangChuanController extends BaseController {

    @RequestMapping(value = "picture", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String uploadPicture2(@RequestParam("file") MultipartFile acct,HttpServletRequest request){
       Map map= FileUploadUtilClass.fileUploadImg(acct,request,50000000,Const.FILEPATHIMG,Const.SERVERPATH,"audio");
        return map.get("picture").toString();
    }

    @RequestMapping(value = "imgUpload", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String imgUpload(@RequestParam("file") MultipartFile acct,HttpServletRequest request){
        Map map= FileUploadUtilClass.fileUploadImg(acct,request,50000000,Const.FILEPATHIMG,Const.SERVERPATH,"image");
        String picture = JSONArray.toJSONString(map.get("picture").toString());
        return picture.replace("\"","");
    }



    @RequestMapping(value = "nativeUpload", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String nativeUpload(@RequestParam("file") MultipartFile acct,HttpServletRequest request){
        Map map= FileUploadUtilClass.nativeUpload(acct,request,50000000,Const.FILEPATHIMG,Const.SERVERPATH);
        return map.get("picture").toString();
    }
}
