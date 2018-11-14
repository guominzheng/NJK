package com.fh.service.system.tiwen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="tiWenService")
public class TiWenService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
    public void save(PageData pd) throws Exception{
    	dao.save("TiWenMapper.save", pd);
    }
    
    public List<PageData> datalistPage(Page page) throws Exception{
    	return (List<PageData>) dao.findForList("TiWenMapper.datalistPage", page);
    }
    
    public List<PageData> data2listPage(Page page) throws Exception{
    	return (List<PageData>) dao.findForList("TiWenMapper.data2listPage", page);
    }
    
    public void delete(PageData pd) throws Exception{
    	dao.delete("TiWenMapper.delete", pd);
    }
    
    public PageData findById(PageData pd) throws Exception{
    	return (PageData) dao.findForObject("TiWenMapper.findById", pd);
    }
    
    public void editHuiFu(PageData pd) throws Exception{
    	dao.update("TiWenMapper.editHuiFu", pd);
    }
    
    public void editViews(PageData pd) throws Exception{
    	dao.update("TiWenMapper.editViews", pd);
    }
    
    public List<PageData> userlistPage(Page page) throws Exception{
    	return (List<PageData>) dao.findForList("TiWenMapper.userlistPage", page);
    }
    
    public void editUserId(PageData pd) throws Exception{
    	dao.update("TiWenMapper.editUserId", pd);
    }
    public List<PageData> dataslistPage(Page page) throws Exception{
    	return (List<PageData>) dao.findForList("TiWenMapper.dataslistPage", page);
    }
    public void editSHENCHA(PageData pd) throws Exception{
        dao.update("TiWenMapper.editSHENCHA", pd);
    }
    public void updateAll(String p[]) throws Exception{
        dao.update("TiWenMapper.updateAll", p);
    }

    /**
     * 混查所有提问和研究院帖子
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> TIRElistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("TiWenMapper.TIRElistPage", page);
    }
}
