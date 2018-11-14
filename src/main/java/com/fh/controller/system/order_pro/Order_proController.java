package com.fh.controller.system.order_pro;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.order_pro.Order_ProService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import sun.security.krb5.internal.PAEncTSEnc;

@Controller
@RequestMapping(value="order_pro")
public class Order_proController extends BaseController{
	
	@Resource(name="order_ProService")
	public Order_ProService order_ProService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/show")
	public ModelAndView list(){
		logBefore(logger, "查询订单商品列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>	varList = order_ProService.findOrderProList(pd);	//列出Order_Info列表
			double Z_MONEY =0.0;
			if(varList!=null){
				pd.put("NAME",varList.get(0).getString("ONAME"));
				pd.put("ADDRESS",varList.get(0).getString("CITY")+varList.get(0).getString("ADDRESS"));
				pd.put("PAY_DATE",varList.get(0).getString("PAY_DATE"));
				pd.put("PHONE",varList.get(0).getString("PHONE"));
				pd.put("MONEY",varList.get(0).get("MONEY").toString());
				pd.put("PAY_MONEY",varList.get(0).get("PAY_MONEY").toString());
				pd.put("PAY_METHOD",varList.get(0).get("PAY_METHOD").toString());
				double JIAN = Double.valueOf(varList.get(0).get("PAY_MONEY").toString())-Double.valueOf(varList.get(0).get("MONEY").toString());
					if(JIAN>=0){
						pd.put("JIAN",JIAN);
					}else{
						pd.put("JIAN","0.0");
					}
				/*pd.put("Z_MONEY",Z_MONEY);*/
			}
			mv.setViewName("system/order_pro/order_pro_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
}
