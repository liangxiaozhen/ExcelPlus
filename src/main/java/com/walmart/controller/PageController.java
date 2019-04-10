package com.walmart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.walmart.common.BaseController;
import com.walmart.model.MyExcel;
import com.walmart.service.MyExcelService;

public class PageController extends BaseController{
	
	
	@Autowired
	MyExcelService myExcelService;
	/**
	 * 获取用户银行卡信息列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/queryBankInfoList", method = { RequestMethod.POST})
	public ModelAndView bankinfolist (HttpServletRequest request,MyExcel userBankCard) throws Exception {		
		
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<String, Object>();
		initPage(map, pageNum, pageSize);
		
		// 调用service层的方法得到对象列表
		List<MyExcel> bankinfolist = myExcelService.selectAll();
		PageInfo<Object> pagehelper = initPagehelper(map, bankinfolist);
		// 条件回显
		String username = request.getParameter("username");
		// 返回ModelAndView
		ModelAndView mv = new ModelAndView();
		mv.addObject("pagehelper", pagehelper);
		mv.addObject("username", username); // 姓名
		// 指定视图
		mv.setViewName("admin/userbankcard/userbankcardList");
		return mv;
	}

}
