package com.fh.controller.system.note;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.note.NoteService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="note",produces="text/html;charset=UTF-8")
public class NoteController extends BaseController{

	@Resource(name="noteService")
	private NoteService noteService;
	
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/save")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改商品");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("NOTE", pd.getString("editorValue"));
		PageData pd1=noteService.findByUId(pd);
		if(pd1==null){
			pd.put("NOTE_ID", this.get32UUID());
			noteService.save(pd);
		}else{
			noteService.edit(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/note")
	public ModelAndView goNote(){
		logBefore(logger, "去修改商品页面");
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", u.getUSER_ID());
		try {	
			pd = noteService.findByUId(pd);	//根据ID读取	
			mv.setViewName("system/note/note_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
}
