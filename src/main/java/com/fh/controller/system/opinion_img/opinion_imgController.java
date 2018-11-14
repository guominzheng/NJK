package com.fh.controller.system.opinion_img;

import com.fh.controller.base.BaseController;
import com.fh.service.system.opinion_img.Opinion_ImgService;
import com.fh.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "opinion_img", produces = "text/html;charset=UTF-8")
public class opinion_imgController extends BaseController {
    @Resource(name = "opinion_ImgService")
    private Opinion_ImgService opinion_imgService;


    /**
     *
     */
    @RequestMapping(value="/saveOpinIMG")
    @ResponseBody
    public Object saveOpinIMG(@RequestParam(required = false) MultipartFile file) throws Exception{
        logBefore(logger, "上传用户建议回复图片");
        Map<String, String> map = new HashMap<String, String>();
        String ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        String id = this.get32UUID();
        pd=this.getPageData();
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
            System.out.println("filePath------------------:"+filePath);
            fileName = FileUpload.fileUp(file, filePath, id); // 执行上传
            System.out.println("fileName------------------:"+fileName);
        } else {
            System.out.println("上传失败");
        }
        pd.put("IMG", Const.SERVERPATH+Const.FILEPATHIMG+ffile+"/"+fileName);
        pd.put("OPINION_ID", this.get32UUID());
        opinion_imgService.save(pd);
        map.put("result", "ok");
        return AppUtil.returnObject(pd, map);
    }
}
