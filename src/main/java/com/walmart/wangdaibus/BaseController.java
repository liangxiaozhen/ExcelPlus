package com.ganjiangps.wangdaibus.common.controller;



import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ganjiangps.wangdaibus.model.AdminUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.ganjiangps.wangdaibus.common.constant.Redis_Constat;
import com.ganjiangps.wangdaibus.common.constant.Session_Constant;
import com.ganjiangps.wangdaibus.common.file.FastDFSUploadComponent;
import com.ganjiangps.wangdaibus.common.jwt.WangdaibusJwtTokenUtil;
import com.ganjiangps.wangdaibus.common.response.ProcessBack;
import com.ganjiangps.wangdaibus.common.util.AES;
import com.ganjiangps.wangdaibus.common.util.CookieUtil;
import com.ganjiangps.wangdaibus.common.util.DateUtils;
import com.ganjiangps.wangdaibus.common.util.RedisComponentUtil;
import com.ganjiangps.wangdaibus.common.util.StateUtil;
import com.ganjiangps.wangdaibus.common.util.StringUtil;
import com.ganjiangps.wangdaibus.model.PreCommonMember;
import com.ganjiangps.wangdaibus.model.PreUcenterMembers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
  
public class BaseController<Entity>{
	
	public static Log logger = LogFactory.getLog(BaseController.class);
	protected HttpSession session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected SimpleDateFormat sf1 =new SimpleDateFormat("yyyyMMdd");
	protected SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
	protected static DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
	protected static DecimalFormat df1 = new DecimalFormat("##########0.00");
	protected static DecimalFormat df0 = new DecimalFormat("###########");
	//格式化页面时间显示
	private static DateUtils du = new DateUtils();
	//状态转换
	private static StateUtil su = new StateUtil();
	//工具类
	private static StringUtil sut = new StringUtil();
	//获取图片服务器域名
	@Value("${wdbus.fdfs.url}")
	private String hostUrl;

	@Resource
	private WangdaibusJwtTokenUtil wangdaibusJwtTokenUtil;
	
	@Resource
	private RedisComponentUtil redisComponentUtil;

	@ModelAttribute  
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response,HttpSession session){  
	    this.response= response;
	    this.request = request;
	    this.session = session;
		String oldCookieValue = CookieUtil.getCookieByName(CookieUtil.WDBCOOKIENAME, request, response);
		if(StringUtil.isEmpty(oldCookieValue)){
		   //写进cookie
		   String cookieValue = "wdb"+StringUtil.getNo();
		   CookieUtil.addCookie(CookieUtil.WDBCOOKIENAME, cookieValue,365*24*60*60, request, response, true);
 		}
  //	System.out.println(new Date()+"--request ------------basecontroller-------------"+request);
//	    System.out.println(new Date()+"--response------------basecontroller-------------"+response);
//	    System.out.println(new Date()+"--session ------------basecontroller-------------"+session);
	}
	
	@ModelAttribute  
	public void common(Model model){
	    model.addAttribute("dateUtils",du);
		model.addAttribute("stateUtil",su);
		model.addAttribute("sut",sut);
		/*model.addAttribute("domain",hostUrl);
		model.addAttribute("xmfDomain","xiaomifeng.shop");*/
		model.addAttribute("df0", df0);
		model.addAttribute("df1", df1);
	}
	
	//初始化分页相关信息
		protected void initPage(Map<String,Object> map, String pageNum, String pageSize){
			Integer num = 1;
			Integer size = 20;

			if (pageNum != null && !"".equals(pageNum)) {
				num = Integer.parseInt(pageNum);
			}
			if (pageSize != null && !"".equals(pageSize)) {
				size = Integer.parseInt(pageSize);
			}
			//String sortString = "id.desc";
		//	Order.formString(sortString);
			PageHelper.startPage(num, size);
//				map.put("startIndex", BaseController.getStartIndex(num,size));
//				map.put("endIndex", BaseController.getStartIndex(num, size)+size);
//				map.put("pageNum", num);
//				map.put("totalPage", totalPage);
			map.put("pageSize", size);
//				map.put("totalCount", totalCount);

		}
		protected  PageInfo<Object> initPagehelper(Map map,List list){
			PageInfo<Object> pagehelper = new PageInfo<Object>(list);
			//pagehelper.setFirstPage(1); //此方法已废弃 改为 setNavigateFirstPage
			pagehelper.setNavigateFirstPage(1);
			Integer lastPageNum =0;
			Integer size = (Integer)map.get("pageSize");

			if(pagehelper.getTotal()%size==0){
				lastPageNum = (int)pagehelper.getTotal()/size;
			}else{
				lastPageNum = (int)pagehelper.getTotal()/size + 1 ;
			}

			//pagehelper.setLastPage(lastPageNum); //此方法已废弃 改为 setNavigateLastPage
			pagehelper.setNavigateLastPage(lastPageNum);
 			return pagehelper;
		}
		
		/**
		 * 
		* @Title: getPreCommonMemberByHtml 
		* @Description: TODO(获取redis中的用户基本信息) 
		* @param @return    设定文件 
		* @return PreCommonMember    返回类型 
		* @throws
		 */
		protected PreCommonMember getPreCommonMemberByHtml(){
			PreCommonMember commonMember = null;
 			try{
 				String uid = wangdaibusJwtTokenUtil.getUserBaseId(request,response);
 				if(StringUtil.isNotEmpty(uid)){
  					commonMember = (PreCommonMember) redisComponentUtil.getRedisByKey(Redis_Constat.USERHTML+uid);
 				}
   			}catch(Exception e){
				e.printStackTrace();
			}
 			return commonMember;
		}
		
		/**
		 * 
		* @Title: getPreUcenterMembersByHtml 
		* @Description: TODO(获取redis中的用户安全基本信息) 
		* @param @return    设定文件 
		* @return PreCommonMember    返回类型 
		* @throws
		 */
		protected PreUcenterMembers getPreUcenterMembersByHtml(){
			PreUcenterMembers members = null;
 			try{
 				String uid = wangdaibusJwtTokenUtil.getUserBaseId(request,response);
 				if(StringUtil.isNotEmpty(uid)){
  					members = (PreUcenterMembers) redisComponentUtil.getRedisByKey(Redis_Constat.USERCENTERHTML+uid);
 				}
 			}catch(Exception e){
				e.printStackTrace();
			}
 			return members;
 		}
		
		
		/**
		 * 
		* @Title: getPreCommonMemberByPC 
		* @Description: TODO(获取session中的用户基本信息) 
		* @param @return    设定文件 
		* @return PreCommonMember    返回类型 
		* @throws
		 */
		protected PreCommonMember getPreCommonMemberByPC(){
			HttpSession session = request.getSession(false);
			if(session == null){
				return null;
			}
			PreCommonMember commonMember = (PreCommonMember) session.getAttribute(Session_Constant.USER);
			return commonMember;
		}
		
		/**
		 * 
		* @Title: getPreCommonMemberByPC 
		* @Description: TODO(获取session中的用户基本信息) 
		* @param @return    设定文件 
		* @return PreCommonMember    返回类型 
		* @throws
		 */
		protected PreCommonMember getPreCommonMemberByPC406(){
			HttpSession session = request.getSession(false);
			if(session == null){
				return null;
			}
			PreCommonMember commonMember = (PreCommonMember) session.getAttribute(Session_Constant.USERACCOUNTINFO406);
			return commonMember;
		}
		
		/**
		 * 
		* @Title: getPreCommonMemberByPC 
		* @Description: TODO(获取session中的用户安全基本信息) 
		* @param @return    设定文件 
		* @return PreCommonMember    返回类型 
		* @throws
		 */
		protected PreUcenterMembers getPreUcenterMembersByPC(){
			HttpSession session = request.getSession(false);
			if(session == null){
				return null;
			}
			PreUcenterMembers preUcenterMembers = (PreUcenterMembers) session.getAttribute(Session_Constant.USERSECURITYINFO);
			return preUcenterMembers;
 		}
		
		/**
		 * 
		* @Title: getAdminUserByAdmin 
		* @Description: TODO(获取session中的管理用户安全基本信息) 
		* @param @return    设定文件 
		* @return AdminUser    返回类型 
		* @throws
		 */
		protected AdminUser getAdminUserByAdmin(){
			AdminUser adminUser = (AdminUser) session.getAttribute("userSession");
			return adminUser;
		}
	  
		
		
		/**
		 * 
		* @Title: getDecryptionPreCommonMember 
		* @Description: TODO(解密返回    数据库加密字段 保存进session) 
		* @param @param PreCommonMember
		* @param @return    设定文件 
		* @return UserBaseAccountInfo    返回类型 
		* @author   cjm  
		* @throws
		 */
		public static PreCommonMember getDecryptionPreCommonMember(PreCommonMember preCommonMember){
			
			if(preCommonMember == null){
				return preCommonMember;
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("解密用户:"+JSON.toJSONString(preCommonMember));
			}
			
			if(preCommonMember.getEmail() != null  && StringUtil.isNotEmpty(preCommonMember.getEmail())){//邮箱
				preCommonMember.setEmail(AES.getDecrypt(preCommonMember.getEmail()));
			}
			
			if(preCommonMember.getMobile() != null && StringUtil.isNotEmpty(preCommonMember.getMobile())){//手机号码
				preCommonMember.setMobile(AES.getDecrypt(preCommonMember.getMobile()));
			}
			
			if(preCommonMember.getCertno() != null && StringUtil.isNotEmpty(preCommonMember.getCertno())){//身份证号码
				preCommonMember.setCertno(AES.getDecrypt(preCommonMember.getCertno()));
			}
			
			if(preCommonMember.getRealname() != null && StringUtil.isNotEmpty(preCommonMember.getRealname())){//身份证姓名
				preCommonMember.setRealname(AES.getDecrypt(preCommonMember.getRealname().trim()));
 			}
			
			if(preCommonMember.getUsername() != null && StringUtil.isNotEmpty(preCommonMember.getUsername())){//用户名
				preCommonMember.setUsername(AES.getDecrypt(preCommonMember.getUsername()));
			}
			
		return preCommonMember;
	}
		
		/**
		 * 
		* @Title: getEncryptPreCommonMember 
		* @Description: TODO(加密返回    更新保存进数据库) 
		* @param @param PreCommonMember
		* @param @return    设定文件 
		* @return UserBaseAccountInfo    返回类型 
		* @author   cjm  
		* @throws
		 */
		public static PreCommonMember getEncryptPreCommonMember(PreCommonMember preCommonMember){
			
			if(preCommonMember == null){
				return preCommonMember;
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("加密用户:"+JSON.toJSONString(preCommonMember));
			}
			
			if(preCommonMember.getEmail() != null  && StringUtil.isNotEmpty(preCommonMember.getEmail())){//邮箱
				preCommonMember.setEmail(AES.getEncrypt(preCommonMember.getEmail()));
			}
			
			if(preCommonMember.getMobile() != null && StringUtil.isNotEmpty(preCommonMember.getMobile())){//手机号码
				preCommonMember.setMobile(AES.getEncrypt(preCommonMember.getMobile()));
			}
			
			if(preCommonMember.getCertno() != null && StringUtil.isNotEmpty(preCommonMember.getCertno())){//身份证号码
				preCommonMember.setCertno(AES.getEncrypt(preCommonMember.getCertno()));
			}
			
			if(preCommonMember.getRealname() != null && StringUtil.isNotEmpty(preCommonMember.getRealname())){//身份证姓名
				preCommonMember.setRealname(AES.getEncrypt(preCommonMember.getRealname().trim()));
 			}
			
			if(preCommonMember.getUsername() != null && StringUtil.isNotEmpty(preCommonMember.getUsername())){//用户名
				preCommonMember.setUsername(AES.getEncrypt(preCommonMember.getUsername()));
			}
			
		return preCommonMember;
	}

	public static AdminUser getAdminUser(){
		try{
			Session session = SecurityUtils.getSubject().getSession();
			return (AdminUser)session.getAttribute("userSession");
		}catch (UnavailableSecurityManagerException e){
			return null;
		}catch (Exception e){
			return null;
		}
	}
	
	/**
	 * 设置Cookie
	 *
	 * @return
	 */
	public String setCookie()
	{
	/*	request.setAttribute("cookieName", );*/
		String regcookie = StringUtil.getCookieValue(request);
		if (StringUtil.isEmpty(regcookie))
		{
			StringUtil.addCookie(response, request);

		}
		return regcookie;
	}
 }
