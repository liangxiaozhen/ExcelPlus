package com.walmart.wangdaibus;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ganjiangps.wangdaibus.AbstractBaseAdminController;
import com.ganjiangps.wangdaibus.common.response.AjaxResult;
import com.ganjiangps.wangdaibus.common.util.StringUtil;
import com.ganjiangps.wangdaibus.model.AdminUser;
import com.ganjiangps.wangdaibus.model.PreCommonMember;
import com.ganjiangps.wangdaibus.model.PreUcenterMembers;
import com.ganjiangps.wangdaibus.model.UserBindAdmin;
import com.ganjiangps.wangdaibus.model.UserPostPermissionSetting;
import com.ganjiangps.wangdaibus.service.LogOperationService;
import com.ganjiangps.wangdaibus.service.PreCommonMemberService;
import com.ganjiangps.wangdaibus.service.PreUcenterMembersService;
import com.ganjiangps.wangdaibus.service.UserBindAdminService;
import com.ganjiangps.wangdaibus.service.UserPostPermissionSettingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
 * Controller
 * 
 * @author Joe
 */
@Controller
@RequestMapping("/admin/preCommonMember")
public class PreCommonMemberController extends AbstractBaseAdminController<PreCommonMember>{
	
	@Resource
	UserPostPermissionSettingService userPostPermissionSettingService;
	@Resource
	UserBindAdminService userBindAdminService;
	@Resource
	PreUcenterMembersService preUcenterMembersService;
	@Resource
	LogOperationService logLoginService;
	@Resource
	PreCommonMemberService preCommonMemberService;
	
	
	/**
	 * 用户茶馆权限列表页
	 */
	@RequestMapping(value = "/authList", method = { RequestMethod.GET, RequestMethod.POST })
	public String authList(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<PreCommonMember> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, preCommonMemberService.dataDecrypt(lists));
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(),getDecryptionPreCommonMember(p));
		return display(VIEW_AUTH_LIST);
	}

	/**
	 * 用户茶馆权限修改页面跳转
	 */
	@RequestMapping(value = "/userPermissionsupdateView/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String userPermissionsupdateView(@PathVariable("id") Long id, Model model) {
		//点击权限设置的时候往数据库插入一条默认记录,然后查询并显示
		PreCommonMember entity = get(id);
		userPostPermissionSettingService.insertDefaultRecord(entity,1);
		UserPostPermissionSetting upp = userPostPermissionSettingService.selectOne(new EntityWrapper<UserPostPermissionSetting>().eq(true, "uid", id).eq(true, "type", 1));
		model.addAttribute("upp",upp);
		return  display(VIEW_AUTH_UPDATE);
	}
	
	/**
	 * 茶馆权限修改 并保存
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authUpdate", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult authUpdate(UserPostPermissionSetting entity) {
		return userPostPermissionSettingService.updateAuthShezhi(entity);
	}

	/**
	 * 茶馆根据id查看详情
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/auth_detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String authView(Model model, @PathVariable("id") Long id) {
		PreCommonMember entity= get(id);
		userPostPermissionSettingService.insertDefaultRecord(entity, 1);//查看详情的时候 查询数据库有无默认值,如果没有就插入默认值
		model.addAttribute("preCommonMember", getDecryptionPreCommonMember(entity));
		UserPostPermissionSetting upp = userPostPermissionSettingService.selectOne(new EntityWrapper<UserPostPermissionSetting>().eq(true, "uid", id).eq(true, "type", 1));
		model.addAttribute("upp",upp);
		model.addAttribute("sf", sf);
		return display(VIEW_AUTH_DETAIL);
	}
	
	
	
	/**
	 * 用户资讯权限列表页
	 */
	@RequestMapping(value = "/authList2", method = { RequestMethod.GET, RequestMethod.POST })
	public String authList2(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<PreCommonMember> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, preCommonMemberService.dataDecrypt(lists));
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(),getDecryptionPreCommonMember(p));
		return display(VIEW_AUTH_LIST2);
	}

	/**
	 * 用户资讯权限修改页面跳转
	 */
	@RequestMapping(value = "/userPermissionsupdateView2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String userPermissionsupdateView2(@PathVariable("id") Long id, Model model) {
		//点击权限设置的时候往数据库插入一条默认记录,然后查询并显示
		PreCommonMember entity = get(id);
		userPostPermissionSettingService.insertDefaultRecord(entity,2);
		UserPostPermissionSetting upp = userPostPermissionSettingService.selectOne(new EntityWrapper<UserPostPermissionSetting>().eq(true, "uid", id).eq(true, "type", 2));
		model.addAttribute("upp",upp);
		return  display(VIEW_AUTH_UPDATE2);
	}
	
	/**
	 * 茶馆权限修改 并保存
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authUpdate2", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult authUpdate2(UserPostPermissionSetting entity) {
		return userPostPermissionSettingService.updateAuthShezhi(entity);
	}

	/**
	 * 茶馆根据id查看详情
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "auth_detail2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String authView2(Model model, @PathVariable("id") Long id) {
		PreCommonMember preCommonMember= get(id);
		userPostPermissionSettingService.insertDefaultRecord(preCommonMember, 2);//查看详情的时候 查询数据库有无默认值,如果没有就插入默认值
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(preCommonMember));
		UserPostPermissionSetting upp = userPostPermissionSettingService.selectOne(new EntityWrapper<UserPostPermissionSetting>().eq(true, "uid", id).eq(true, "type", 2));
		model.addAttribute("upp",upp);
		model.addAttribute("sf", sf);
		return display(VIEW_AUTH_DETAIL2);
	}
	
	
	/**
	 * 用户查看列表页
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		//把所有的id 都分别存入一个对象
		List<PreCommonMember> lists = preCommonMemberService.getAllListPreCommonMember(afterHandleEntity);
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, preCommonMemberService.dataDecrypt(lists));
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(),getDecryptionPreCommonMember(p));
		return display(VIEW_LIST);
	}
	

	/**
	 * 用户修改页面跳转
	 */
	@RequestMapping(value = "/updateView/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateView(@PathVariable("id") Long id, Model model) {
		PreCommonMember entity = get(id);
		session.setAttribute("beforePreCommonMember", entity);
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(entity));
		return display(VIEW_UPDATE);
	}
	
	/**
	 * 用户更新
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult update(PreCommonMember entity) {
		AjaxResult ajaxResult = AjaxResult.createAjaxResult();
		if(!preUpdate(ajaxResult,entity)){
			return ajaxResult;
		};
		PreCommonMember beforePreCommonMember= (PreCommonMember) session.getAttribute("beforePreCommonMember");
		PreCommonMember preCommonMember = getEncryptPreCommonMember(entity);
		ajaxResult = preCommonMemberService.updateVlidata(preCommonMember);
		if(ajaxResult.getMeta().getCode().equals("88")){
			try {
				boolean flag = preCommonMemberService.updateById(preCommonMember);
				if (flag) {
					logLoginService.saveLogOpertion(getAdminUserByAdmin(), getDecryptionPreCommonMember(preCommonMember), beforePreCommonMember, 1, 2, entity.getUid(), "preCommonMember", request);
					return ajaxResult.successAjaxResult("更新成功");
				}else{//更新失败
					logLoginService.saveLogOpertion(getAdminUserByAdmin(), getDecryptionPreCommonMember(preCommonMember), beforePreCommonMember, 2, 2, entity.getUid(), "preCommonMember", request);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ajaxResult.failAjaxResult("因网络响应不及时,操作失败,请联系客服或重新操作");
			}
			return ajaxResult.failAjaxResult("更新失败");
		}
		return ajaxResult;
	}

	/**
	 * 根据id查看详情
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String view(Model model, @PathVariable("id") Long id) {
		PreCommonMember preCommonMember = getDecryptionPreCommonMember(get(id));
		PreUcenterMembers pMembers =  preUcenterMembersService.selectOne(new EntityWrapper<PreUcenterMembers>().eq(true, "uid", preCommonMember.getUid()));
		if(!StringUtil.isEmpty(pMembers)){
			if(!StringUtil.isEmpty(pMembers.getRegip())){
				preCommonMember.setRegip(pMembers.getRegip());
			}
			if(!StringUtil.isEmpty(pMembers.getLastloginip())){
				preCommonMember.setLastloginip(pMembers.getLastloginip());
			}
		}
		UserBindAdmin userBindAdmin  = userBindAdminService.selectOne(new EntityWrapper<UserBindAdmin>().eq(true, "uid", preCommonMember.getUid()).eq(true, "status", 1));
		if(!StringUtil.isEmpty(userBindAdmin)){
			preCommonMember.setIsadmin(1);
		}else{
			preCommonMember.setIsadmin(0);
		}
		model.addAttribute(getSimpleName(), preCommonMember);
		model.addAttribute("sf", sf);
		return display(VIEW_DETAIL);
	}
	
	/**
	 * 用户修改列表页
	 * @param @param model
	 * @param @param entity
	 * @param @return
	 * @param @throws IllegalAccessException
	 * @return String
	 * @author jiangxueyou
	 */
	@RequestMapping(value = "/update_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String update_list(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "regdate desc";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<PreCommonMember> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));
		
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, preCommonMemberService.dataDecrypt(lists));
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		//获取到当前登录的管理员对象
		String userAuth = "0";
		AdminUser adminUser = getAdminUserByAdmin();
		if(!StringUtil.isEmpty(adminUser)){
			if(adminUser.getUsername().equals("jiang")){
				userAuth = "1";
			}
		}
		model.addAttribute("userAuth", userAuth);
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(p));
		return display(UPDATE_VIEW_LIST);
	}
	
	/**
	 * 客服列表页
	 * @param @param model
	 * @param @param entity
	 * @param @return
	 * @param @throws IllegalAccessException
	 * @return String
	 * @author jiangxueyou
	 */
	@RequestMapping(value = "/customerList", method = { RequestMethod.GET, RequestMethod.POST })
	public String customerList(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "regdate desc";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<PreCommonMember> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));
		lists = preCommonMemberService.dataDecrypt(lists);
		if(lists.size()>0){
			for (PreCommonMember userAccountInfo2 : lists) {
				 //处理电话号码和身份证
				userAccountInfo2.setMobile(chuliStr2(userAccountInfo2.getMobile()));
				userAccountInfo2.setCertno(chuliStr(userAccountInfo2.getCertno()));
				userAccountInfo2.setRealname(chuliStr3(userAccountInfo2.getRealname()));
				
			}
		}
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, lists);
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(p));
		return display(CUSTOMER_VIEW_LIST);
	}

	/**
	 * 客服详情操作页面
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @author jiangxueyou
	 */
	@RequestMapping(value = "/customerList/deail", method = { RequestMethod.GET, RequestMethod.POST })
	public String customerList_deail(Model model,String id) {
		PreCommonMember preCommonMember = new PreCommonMember();
		if(!StringUtil.isEmpty(id)){
			preCommonMember = preCommonMemberService.selectById(id);
			preCommonMember = preCommonMemberService.dataDecrypt(preCommonMember);
			PreUcenterMembers pMembers =  preUcenterMembersService.selectOne(new EntityWrapper<PreUcenterMembers>().eq(true, "uid", preCommonMember.getUid()));
			if(!StringUtil.isEmpty(pMembers)){
				if(!StringUtil.isEmpty(pMembers.getRegip())){
					preCommonMember.setRegip(pMembers.getRegip());
				}
				if(!StringUtil.isEmpty(pMembers.getLastloginip())){
					preCommonMember.setLastloginip(pMembers.getLastloginip());
				}
			}
			System.out.println("这个是查询到的对象"+preCommonMember);
			if(!StringUtil.isEmpty(preCommonMember)){
				preCommonMember.setMobile(chuliStr2(preCommonMember.getMobile()));
				preCommonMember.setCertno(chuliStr(preCommonMember.getCertno()));
				preCommonMember.setRealname(chuliStr3(preCommonMember.getRealname()));
				UserBindAdmin userBindAdmin  = userBindAdminService.selectOne(new EntityWrapper<UserBindAdmin>().eq(true, "uid", preCommonMember.getUid()).eq(true, "status", 1));
				if(!StringUtil.isEmpty(userBindAdmin)){
					preCommonMember.setIsadmin(1);
				}else{
					preCommonMember.setIsadmin(0);
				}
			}
		}
		model.addAttribute(getSimpleName(), preCommonMember);
		return display(VIEW_DETAIL);
		
	}
	/**
	 * 处理名字
	 * @param @param name
	 * @param @return
	 * @return String
	 * @author jiangxueyou
	 */
	public static String chuliStr3(String name){
		String result = "";
		if(!StringUtil.isEmpty(name)){
			String st = name.substring(0, 1);
			result = st+"**";
		}
		
		return result;
	}
	
	/**
	 * 处理电话号码
	 * @param @param str
	 * @param @return
	 * @return String
	 * @author jiangxueyou
	 */
	public String chuliStr2(String str){
		String result = "";
		if(!StringUtil.isEmpty(str)){
			String st = str.substring(0, 3);
			String st1 = str.substring(str.length()-4, str.length());
			result = st+"****"+st1;
		}
		
		return result;
	}
	
	/**
	 * 处理身份证
	 * @param @param str
	 * @param @return
	 * @return String
	 * @author jiangxueyou
	 */
	public String chuliStr(String str){
		String result = "";
		if(!StringUtil.isEmpty(str)){
			String st = str.substring(0, 6);
			String st1 = str.substring(str.length()-4, str.length());
			result = st+"**********"+st1;
		}
		
		return result;
	}

	/**
	 * 茶馆权限修改 并保存
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public void addRecord() {
		preCommonMemberService.addRecord();
	}	
	
	/**
	 * 业务员设置列表
	 * @param @param model
	 * @param @param entity
	 * @param @return
	 * @param @throws IllegalAccessException
	 * @return String
	 * @author jiangxueyou
	 */
	@RequestMapping(value = "/salesman_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String salesman_list(Model model, PreCommonMember entity) throws IllegalAccessException {
		PreCommonMember p = getEncryptPreCommonMember(entity);
		PreCommonMember afterHandleEntity = changeNullStringToNull(p);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		String orderBy= "regdate desc";
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<PreCommonMember> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));
		
		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, preCommonMemberService.dataDecrypt(lists));
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		//获取到当前登录的管理员对象
		String userAuth = "0";
		AdminUser adminUser = getAdminUserByAdmin();
		if(!StringUtil.isEmpty(adminUser)){
			if(adminUser.getUsername().equals("jiang")){
				userAuth = "1";
			}
		}
		model.addAttribute("userAuth", userAuth);
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(p));
		return display(SALESMAN_VIEW_LIST);
	}
	
	/**
	 * 用户修改页面跳转
	 */
	@RequestMapping(value = "/salesmaniew/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String salesmaniew(@PathVariable("id") Long id, Model model) {
		PreCommonMember entity = get(id);
		session.setAttribute("salesmanbeforePreCommonMember", entity);
		model.addAttribute(getSimpleName(), getDecryptionPreCommonMember(entity));
		return display(SALESMAN_VIEW_UPDATE);
	}
	
	@ResponseBody
	@RequestMapping(value = "/salesmanupdate", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult salesmanupdate(PreCommonMember entity) {
		AjaxResult ajaxResult = AjaxResult.createAjaxResult();
		/*if(!preUpdate(ajaxResult,entity)){
			return ajaxResult;
		};*/
		PreCommonMember beforePreCommonMember= (PreCommonMember) session.getAttribute("salesmanbeforePreCommonMember");
		PreCommonMember preCommonMember = getEncryptPreCommonMember(entity);
		try {
			boolean flag = preCommonMemberService.updateById(preCommonMember);
			if (flag) {
				logLoginService.saveLogOpertion(getAdminUserByAdmin(), getDecryptionPreCommonMember(preCommonMember), beforePreCommonMember, 1, 2, entity.getUid(), "preCommonMember", request);
				return ajaxResult.successAjaxResult("更新成功");
			}else{//更新失败
				logLoginService.saveLogOpertion(getAdminUserByAdmin(), getDecryptionPreCommonMember(preCommonMember), beforePreCommonMember, 2, 2, entity.getUid(), "preCommonMember", request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxResult.failAjaxResult("因网络响应不及时,操作失败,请联系客服或重新操作");
		}
		ajaxResult.failAjaxResult("更新失败");
		
		return ajaxResult;
	}

}