package com.fh.controller.system.wxController;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.wx_classRoom.*;
import com.fh.util.*;
import com.fh.util.quratz.QuratzRun;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/wxController"})
public class WxController extends BaseController {

    @Autowired
    private LiveService liveService;
    @Autowired
    private WxClassRoomUserService wxClassRoomUserService;
    @Autowired
    private ProposService proposService;
    @Autowired
    private WxBannerService wxBannerService;
    @Autowired
    private QuratzService quratzService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private WxSocketTypeService wxSocketTypeService;
    @Autowired
    private ZixunService zixunService;
    @Autowired
    private WxOpinionService opinionService;
    @Autowired
    private BoomService boomService;


    @RequestMapping(value = {"/liveList"}, produces = {"text/html;charset=utf-8"})
    public String getLiveList(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "liveList========================>");
        Page page = getThisPage(getPage(), getPageData());
        request.setAttribute("page", page);
        request.setAttribute("varList", liveService.findLiveList(page, "ClassRoom_LiveMapper.findLiveListlistPage"));
        return "system/wx_live/live_list";
    }

    @RequestMapping(value = {"/opinionList"}, produces = {"text/html;charset=utf-8"})
    public String opinionList(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "opinionList========================\u300B");
        Page page = getThisPage(getPage(), getPageData());
        request.setAttribute("page", page);
        request.setAttribute("varList", opinionService.findAll(page));
        return "system/wx_opinion/opinion_list";
    }

    @RequestMapping(value = {"/zixunList"}, produces = {"text/html;charset=utf-8"})
    public String getZixunList(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u67E5\u8BE2\u54A8\u8BE2List==========================\u300B");
        List varList = null;
        Page page = null;
        try {
            page = getThisPage(getPage(), getPageData());
            varList = zixunService.findZixun(page);
            if (null != varList && varList.size() > 0) {
                for (int i = 0; i < varList.size(); i++) {
                    PageData dto = (PageData) varList.get(i);
                    String str = dto.getString("zixun_context");
                    if (str.length() > 40) {
                        str = (new StringBuilder()).append(str.substring(0, 40)).append("...").toString();
                        dto.put("zixun_context", str);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("varList", varList);
        request.setAttribute("page", page);
        return "system/wx_zixun/zixun_list";
    }

    @RequestMapping(value = {"/proposList"}, produces = {"text/html;charset=utf-8"})
    public String proposList(HttpServletRequest request) {
        logBefore(logger, "\u7533\u8BF7\u8BB2\u5E08List========================\u300B");
        Page page = getThisPage(getPage(), getPageData());
        request.setAttribute("page", page);
        request.setAttribute("varList", proposService.findProposList(page, "ClassRoom_ProposMapper.findProposListlistPage").get("data"));
        return "system/wx_propos/propos_list";
    }

    @RequestMapping(value = {"/bannerList"}, produces = {"text/html;charset=utf-8"})
    public String bannerList(HttpServletRequest request) {
        logBefore(logger, "\u5934\u56FEList========================\u300B");
        Page page = getThisPage(getPage(), getPageData());
        request.setAttribute("page", page);
        request.setAttribute("varList", wxBannerService.findBannerList(page, "ClassRoom_BannerMapper.findAllBannerListlistPage").get("data"));
        return "system/wx_banner/banner_list";
    }

    @RequestMapping(value = {"/liveEdit"}, produces = {"text/html;charset=utf-8"})
    public String liveEdit(HttpServletRequest request, ModelAndView modelAndView)
            throws Exception {
        logBefore(logger, "\u4FEE\u6539\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4========================\u300B");
        request.setAttribute("msg", "liveEditDo");
        PageData pd = new PageData();
        List user = new ArrayList();
        pd.put("cr_teacherStatus", "1");
        user = (List) wxClassRoomUserService.findUserList(pd).get("data");
        request.setAttribute("pd", liveService.one(getThisPage(getPage(), getPageData()), "ClassRoom_LiveMapper.findLiveById", "1").get("live"));
        request.setAttribute("users", user);
        request.setAttribute("type", wxSocketTypeService.findAllType(null));
        return "system/wx_live/live_edit";
    }

    public static void main(String[] args) {
        System.out.println("\u4FEE\u6539\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4");
    }

    @RequestMapping(value = {"/bannerEdit"}, produces = {"text/html;charset=utf-8"})
    public String bannerEdit(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u4FEE\u6539\u5934\u56FE========================\u300B");
        System.out.println(getPageData().get("banner_id"));
        request.setAttribute("msg", "bannerEditDo");
        request.setAttribute("liveList", liveService.findAllLive());
        request.setAttribute("pd", wxBannerService.findBannerOne(getPageData(), "ClassRoom_BannerMapper.findBannerById").get("data"));
        return "system/wx_banner/banner_edit";
    }

    @RequestMapping(value = {"/opinionEdit"}, produces = {"text/html;charset=utf-8"})
    public String opinionEdit(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u4FEE\u6539\u610F\u89C1\u5BA1\u6279========================\u300B");
        request.setAttribute("pd", getPageData());
        request.setAttribute("msg", "opinionEditDo");
        return "system/wx_opinion/opinion_edit";
    }

    @RequestMapping(value = {"/opinionEditDo"}, produces = {"text/html;charset=utf-8"})
    public String opinionEditDo(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u4FEE\u6539\u610F\u89C1\u5BA1\u6279..========================\u300B");
        PageData pd = getPageData();
        try {
            opinionService.update(pd);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "save_result";
    }

    @ResponseBody
    @RequestMapping(value = {"/bannerDeltP"}, produces = {"text/html;charset=utf-8"})
    public String bannerDeltP(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u5934\u56FE========================\u300B");
        PageData pd = getPageData();
        pd.put("banner_Img", "");
        try {
            wxBannerService.update(pd, "ClassRoom_BannerMapper.update");
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @RequestMapping(value = {"/proposEdit"}, produces = {"text/html;charset=utf-8"})
    public String proposEdit(HttpServletRequest request) {
        logBefore(logger, "\u4FEE\u6539\u8BB2\u5E08\u5BA1\u6279\u72B6\u6001========================\u300B");
        request.setAttribute("msg", "proposEditDo");
        request.setAttribute("pd", proposService.findProposById(getPageData(), "ClassRoom_ProposMapper.findProposById").get("data"));
        return "system/wx_propos/propost_edit";
    }

    @RequestMapping(value = {"/liveEditDo"}, produces = {"text/html;charset=utf-8"})
    public String liveEditDo(HttpServletRequest request) {
        logBefore(logger, "\u4FEE\u6539\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4do...========================\u300B");
        PageData pd = getPageData();
        pd.put("live_text", pd.getString("editorValue"));
        pd.put("userId", ((PageData) wxClassRoomUserService.findUserByCondition(pd, "ClassRoom_UserMapper.findUserByCondition").get("data")).get("cr_userid").toString());
        liveService.updateLive(pd, "ClassRoom_LiveMapper.update");
        request.setAttribute("msg", "success");
        return "save_result";
    }

    @RequestMapping(value = {"/bannerEditDo"}, produces = {"text/html;charset=utf-8"})
    public String bannerEditDo(HttpServletRequest request) {
        logBefore(logger, "\u4FEE\u6539\u5934\u56FEdo...========================\u300B");
        wxBannerService.update(getPageData(), "ClassRoom_BannerMapper.update");
        request.setAttribute("msg", "success");
        return "save_result";
    }

    @RequestMapping(value = {"/proposEditDo"}, produces = {"text/html;charset=utf-8"})
    public String proposEditDo(HttpServletRequest request) {
        logBefore(logger, "\u4FEE\u6539\u8BB2\u5E08\u5BA1\u6279\u72B6\u6001do...========================\u300B");
        SmsBao smsBao = new SmsBao();
        PageData pd = getPageData();
        proposService.update(pd, "ClassRoom_ProposMapper.updateStatus");
        int index = Integer.parseInt(pd.get("propos_status").toString());
        if (index == 2)
            try {
                smsBao.sendSMS(pd.getString("propos_userPhone"), "\u5C0A\u656C\u7684\u5BA2\u6237\uFF0C\u60A8\u7533\u8BF7\u7684\u8BB2\u5E08\u672A\u4E88\u6279\u51C6\uFF0C\u8BE6\u7EC6\u60C5\u51B5\u8BF7\u8054\u7CFB\u519C\u6781\u5BA2\u5BA2\u670D\uFF01 0371-51");
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if (index == 3)
            try {
                smsBao.sendSMS(pd.getString("propos_userPhone"), "\u5C0A\u656C\u7684\u5BA2\u6237\uFF0C\u60A8\u7533\u8BF7\u7684\u8BB2\u5E08\u5BA1\u6838\u901A\u8FC7\uFF01");
                PageData pro = (PageData) proposService.findProposById(pd, "ClassRoom_ProposMapper.findProposById").get("data");
                if (pro != null) {
                    PageData dto = new PageData();
                    dto.put("cr_userid", pro.get("propos_userId").toString());
                    dto.put("cr_teacherStatus", "1");
                    wxClassRoomUserService.updateMoney(dto, "ClassRoom_UserMapper.update");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        request.setAttribute("msg", "success");
        return "save_result";
    }

    @RequestMapping(value = {"/liveSave"}, produces = {"text/html;charset=utf-8"})
    public String liveSave(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u6DFB\u52A0\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4========================\u300B");
        List user = null;
        PageData pd = new PageData();
        pd.put("cr_teacherStatus", "1");
        user = (List) wxClassRoomUserService.findUserList(pd).get("data");
        request.setAttribute("msg", "liveSaveDo");
        request.setAttribute("users", user);
        request.setAttribute("type", wxSocketTypeService.findAllType(null));
        request.setAttribute("index", "1");
        return "system/wx_live/live_edit";
    }

    @RequestMapping(value = {"/liveSaveDo"}, produces = {"text/html;charset=utf-8"})
    public String liveSaveDo(HttpServletRequest request) {
        logBefore(logger, "\u6DFB\u52A0\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4do...========================\u300B");
        PageData pd = getPageData();
        try {
            String id = get32UUID();
            pd.put("live_id", id);
            StringBuffer str = new StringBuffer(pd.getString("editorValue"));
            str.insert(7, " style=\"width:100%\" ");
            String a = new String(str);
            pd.put("live_text", a);
            pd.put("live_roomId", "0");
            pd.put("live_view", "0");
            pd.put("createTime", DateUtil.getTime());
            pd.put("updateTime", DateUtil.getTime());
            String beginTime = pd.getString("beginTime");
            String beforTime = "5";
            (new QuratzRun(quratzService, id, beginTime, beforTime, liveService, Integer.valueOf(5))).start();
            System.out.println("\u6211\u5148\u51FA\u6765==================\u300B");
            liveService.saveLive(pd, "ClassRoom_LiveMapper.save");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save_result";
    }

    @RequestMapping(value = {"/saveAudio"}, produces = {"text/html;charset=utf-8"})
    public String saveAudio(HttpServletRequest request) {
        logBefore(logger, "\u6DFB\u52A0\u97F3\u9891========================\u300B");
        List user = null;
        PageData pd = getPageData();
        pd.put("cr_teacherStatus", "1");
        user = (List) wxClassRoomUserService.findUserList(pd).get("data");
        request.setAttribute("msg", "saveAudioDo");
        request.setAttribute("users", user);
        request.setAttribute("pd", pd);
        return "system/wx_live/audio_save";
    }

    @RequestMapping(value = {"/sendModel"}, produces = {"text/html;charset=utf-8"})
    public String sendModel(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u53D1\u5E03\u5C0F\u7A0B\u5E8F\u8BFE\u7A0B...========================\u300B");
        PageData pds = null;
        try {
            pds = getPageData();
            PageData pd = liveService.findLiveByIdWX(pds);
            List boomList = boomService.findAll(new PageData());
            PageData user = wxClassRoomUserService.getUserById(pd);
            String typeId = pd.get("typeId").toString();
            String page = "";
            if ("1".equals(typeId) || "2".equals(typeId) || "3".equals(typeId))
                page = (new StringBuilder()).append("pages/project/live/liveinfo/liveinfo?id=").append(pd.getString("live_id")).toString();
            else if ("4".equals(typeId) || "5".equals(typeId))
                page = (new StringBuilder()).append("pages/project/video/video?id=").append(pd.getString("live_id")).toString();
            if (null != boomList && boomList.size() > 0) {
                for (int i = 0; i < boomList.size(); i++) {
                    PageData voForm = (PageData) boomList.get(i);
                    String strs[] = {
                            pd.getString("live_name"), user.getString("cr_userName"), pd.getString("beginTime"), "\u60A8\u6709\u65B0\u7684\u8BFE\u7A0B\u5F85\u60A8\u67E5\u6536\uFF01"
                    };
                    net.sf.json.JSONObject jsonObject = ModeUtil.XiaoChengXun(strs);
                    ModeUtil.wxMessageModeSend("cdNsmR2lazieOKzZmHXhUBJaViwkJonX59EffsbSX1c", voForm.getString("openId"), page, voForm.getString("formId"), jsonObject);
                    boomService.modifyState(voForm);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/saveAudioDo"}, produces = {"text/html;charset=utf-8"})
    public String saveAudioDo(HttpServletRequest request)
            throws Exception {
        PageData pd = getPageData();
        pd.put("liveId", pd.get("record_liveId").toString());
        PageData live = (PageData) liveService.findLiveOne("ClassRoom_LiveMapper.findLiveOne", pd).get("data");
        String audio_size = request.getParameter("size");
        pd.put("record_size", audio_size);
        pd.put("teacher", "true");
        pd.put("record_userId", live.get("userId").toString());
        pd.put("updateTime", DateUtil.getTime());
        pd.put("createTime", DateUtil.getTime());
        recordService.saveRe(pd);
        return "save_result";
    }

    @ResponseBody
    @RequestMapping(value = {"/deleteLiveImg"}, produces = {"text/html;charset=utf-8"})
    public String deleteLiveImg(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u56FE\u7247========================\u300B");
        PageData pd = getPageData();
        String type = pd.get("type").toString();
        if ("1".equals(type))
            pd.put("live_img", "");
        else if ("2".equals(type))
            pd.put("live_bannerImg", "");
        else if ("3".equals(type))
            pd.put("live_audioImg", "");
        else if ("4".equals(type))
            pd.put("shareImg", "");
        liveService.updateLive(pd, "ClassRoom_LiveMapper.uptateImg");
        return "success";
    }

    @RequestMapping(value = {"/bannerSave"}, produces = {"text/html;charset=utf-8"})
    public String bannerSave(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u6DFB\u52A0\u5934\u56FE========================\u300B");
        request.setAttribute("msg", "bannerSaveDo");
        request.setAttribute("liveList", liveService.findAllLive());
        return "system/wx_banner/banner_edit";
    }

    @RequestMapping(value = {"/zixunSave"}, produces = {"text/html;charset=utf-8"})
    public String zixunSave(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u6DFB\u52A0\u54A8\u8BE2========================\u300B");
        request.setAttribute("msg", "zixunSaveDo");
        return "system/wx_zixun/zixun_edit";
    }

    @RequestMapping(value = {"/zixunEdit"}, produces = {"text/html;charset=utf-8"})
    public String zixunEdit(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "\u4FEE\u6539\u54A8\u8BE2========================\u300B");
        request.setAttribute("msg", "zixunEditDo");
        request.setAttribute("pd", zixunService.findZixunOne(getPageData()));
        return "system/wx_zixun/zixun_edit";
    }

    @RequestMapping(value = {"/zixunSaveDo"}, produces = {"text/html;charset=utf-8"})
    public String zixunSaveDo() {
        logBefore(logger, "\u6DFB\u52A0\u54A8\u8BE2...========================\u300B");
        PageData pd = getPageData();
        try {
            PageData dto = new PageData();
            dto.put("zixun_title", pd.getString("zixun_title"));
            dto.put("zixun_view", "0");
            dto.put("zixun_context", pd.getString("zixun_context"));
            dto.put("createTime", DateUtil.getTime());
            zixunService.save(dto, "ClassRoom_ZixunMapper.save");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save_result";
    }

    @RequestMapping(value = {"/zixunEditDo"}, produces = {"text/html;charset=utf-8"})
    public String zixunEditDo(HttpServletRequest request) {
        logBefore(logger, " \u4FEE\u6539\u54A8\u8BE2...========================\u300B");
        PageData pd = getPageData();
        try {
            //pd.put("zixun_context", DelHTMLTag.delHTMLTag(pd.getString("editorValue")));
            zixunService.update(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save_result";
    }

    @RequestMapping(value = {"/bannerSaveDo"}, produces = {"text/html;charset=utf-8"})
    public String bannerSaveDo(HttpServletRequest request) {
        logBefore(logger, "\u6DFB\u52A0\u5934\u56FEdo...========================\u300B");
        PageData pd = getPageData();
        pd.put("createTime", DateUtil.getTime());
        wxBannerService.save(pd, "ClassRoom_BannerMapper.save");
        return "save_result";
    }

    @RequestMapping(value = {"/liveDeleteAll"}, produces = {"text/html;charset=utf-8"})
    public String liveDeleteAll(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4ALL========================\u300B");
        PageData pd = getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                pd.put("live_id", ArrayDATA_IDS[i]);
                liveService.deleteLive(pd, "ClassRoom_LiveMapper.delete");
            }

        }
        return "success";
    }

    @RequestMapping(value = {"/liveDelete"}, produces = {"text/html;charset=utf-8"})
    public String liveDelete(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u8BB2\u8BFE\u5C0F\u7A0B\u5E8F\u76F4\u64AD\u95F4========================\u300B");
        liveService.deleteLive(getPageData(), "ClassRoom_LiveMapper.delete");
        return "success";
    }

    @RequestMapping(value = {"/bannerDeleteAll"}, produces = {"text/html;charset=utf-8"})
    public String bannerDeleteAll(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u5934\u56FEALL========================\u300B");
        PageData pd = getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                pd.put("banner_id", ArrayDATA_IDS[i]);
                wxBannerService.delete(pd, "ClassRoom_BannerMapper.delete");
            }

        }
        return "success";
    }

    @RequestMapping(value = {"/bannerDelete"}, produces = {"text/html;charset=utf-8"})
    public String bannerDelete(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u5934\u56FE========================\u300B");
        wxBannerService.delete(getPageData(), "ClassRoom_BannerMapper.delete");
        return "success";
    }

    @RequestMapping(value = {"/zixunDelete"}, produces = {"text/html;charset=utf-8"})
    public String zixunDelete(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u54A8\u8BE2========================\u300B");
        try {
            zixunService.delete(getPageData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/zixunDeleteAll"}, produces = {"text/html;charset=utf-8"})
    public String zixunDeleteAll(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u54A8\u8BE2all========================\u300B");
        PageData pd = null;
        try {
            pd = getPageData();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                    pd.put("zixun_id", ArrayDATA_IDS[i]);
                    zixunService.delete(pd);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/typeList"}, produces = {"text/html;charset=utf-8"})
    public String typeList(HttpServletRequest request) {
        logBefore(logger, "typeList========================\u300B");
        PageData pd = null;
        Page page = null;
        try {
            page = getThisPage(getPage(), getPageData());
            request.setAttribute("page", page);
            request.setAttribute("varList", wxSocketTypeService.findAll(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/wx_type/type_list";
    }

    @RequestMapping(value = {"/typeSave"}, produces = {"text/html;charset=utf-8"})
    public String typeSave(HttpServletRequest request) {
        logBefore(logger, "typeSave========================\u300B");
        request.setAttribute("msg", "typeSaveDo");
        return "system/wx_type/type_edit";
    }

    @RequestMapping(value = {"/typeSaveDo"}, produces = {"text/html;charset=utf-8"})
    public String typeSaveDo(HttpServletRequest request) {
        logBefore(logger, "typeSaveDo========================\u300B");
        PageData pd = null;
        try {
            pd = getPageData();
            wxSocketTypeService.save(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save_result";
    }

    @RequestMapping(value = {"/typeEdit"}, produces = {"text/html;charset=utf-8"})
    public String typeEdit(HttpServletRequest request)
            throws Exception {
        logBefore(logger, "typeSave========================\u300B");
        request.setAttribute("msg", "typeEditDo");
        request.setAttribute("pd", wxSocketTypeService.findOne(getPageData()));
        return "system/wx_type/type_edit";
    }

    @RequestMapping(value = {"/typeEditDo"}, produces = {"text/html;charset=utf-8"})
    public String typeEditDo(HttpServletRequest request) {
        logBefore(logger, "typeSaveDo========================\u300B");
        PageData pd = null;
        try {
            pd = getPageData();
            wxSocketTypeService.update(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save_result";
    }

    @RequestMapping(value = {"/typeDelete"}, produces = {"text/html;charset=utf-8"})
    public String typeDelete(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u7C7B\u578B========================\u300B");
        try {
            wxSocketTypeService.delete(getPageData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/typeDeleteAll"}, produces = {"text/html;charset=utf-8"})
    public String typeDeleteAll(HttpServletRequest request) {
        logBefore(logger, "\u5220\u9664\u7C7B\u578Ball========================\u300B");
        PageData pd = null;
        try {
            pd = getPageData();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                    pd.put("type_id", ArrayDATA_IDS[i]);
                    wxSocketTypeService.delete(pd);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    private int getPageNum(PageData pd) {
        int num = 0;
        try {
            num = Integer.parseInt(pd.get("currentPage").toString());
        } catch (NullPointerException e) {
            num = 1;
        }
        return num;
    }

    private int getShowPageNum(PageData pd) {
        int num = 0;
        try {
            num = Integer.parseInt(pd.get("showCount").toString());
        } catch (NullPointerException e) {
            num = 10;
        }
        return num;
    }

    private Page getThisPage(Page page, PageData pd) {
        page.setCurrentPage(getPageNum(pd));
        page.setShowCount(getShowPageNum(pd));
        page.setPd(pd);
        return page;
    }
}
