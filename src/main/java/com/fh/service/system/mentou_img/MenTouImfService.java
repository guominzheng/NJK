package com.fh.service.system.mentou_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="menTouImfService")
public class MenTouImfService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	//批量更新
	public void save(PageData pd) throws Exception{
		dao.save("MentouImgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("MentouImgMapper.findList", pd);
	}
}
