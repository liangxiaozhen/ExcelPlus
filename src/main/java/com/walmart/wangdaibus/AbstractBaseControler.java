package com.ganjiangps.wangdaibus.common.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.IService;
import com.ganjiangps.wangdaibus.common.constant.LogOperationConstant;
import com.ganjiangps.wangdaibus.common.constant.LogOperationData_Constant;
import com.ganjiangps.wangdaibus.common.util.AddressUtils;
import com.ganjiangps.wangdaibus.common.util.StringUtil;
import com.ganjiangps.wangdaibus.model.AdminUser;
import com.ganjiangps.wangdaibus.model.LogOperation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
* @ClassName: AbstractBaseControler 
* @Description: TODO(通用 Controller) 
* @author cjm
* @date 2018年4月20日 下午2:15:02 
 * @param <Entity>
 * 
 * 注意：
 * 这里只实现Controller 的列表，新增，修改，删除 页面显示,操作保存数据库请继承另外实现
 * 
 */
public abstract class AbstractBaseControler<Entity> extends BaseController<Entity>{
	
	/*
	 * 页面后缀
	 */
	public static final String VIEW_LIST   = "_List"; //操作列表
	public static final String VIEWLIST_LIST   = "View_List"; //客服查看列表
 	public static final String UPDATE_VIEW_LIST   = "_Update_List"; //用户修改页面
 	public static final String SALESMAN_VIEW_LIST   = "_Salesman_List"; //业务员设置页面
	public static final String CUSTOMER_VIEW_LIST   = "_Customer_List"; //客服页面
	public static final String VIEW_INSERT = "_Insert";
	public static final String VIEW_UPDATE = "_Update";
	public static final String SALESMAN_VIEW_UPDATE = "_Salesman_Update"; //业务员修改页面
	public static final String VIEW_DELETE = "_Delete";
	public static final String VIEW_DETAIL = "_Detail";
	public static final String VIEW_AUTH_LIST = "_auth_list"; //茶馆权限列表
	public static final String VIEW_AUTH_LIST2 = "_auth_list2"; //资讯权限列表
	public static final String VIEW_AUTH_DETAIL = "_auth_Detail"; //茶馆权限详情
	public static final String VIEW_AUTH_DETAIL2 = "_auth_Detail2"; //资讯权限详情
	public static final String VIEW_AUTH_UPDATE = "_auth_update"; //茶馆权限修改页面
	public static final String VIEW_AUTH_UPDATE2 = "_auth_update2"; //资讯权限修改页面

	//广告图片页面后缀
	public static final String BASIC_VIEW_LIST   = "_Review_List"; //广告图审核列表
	public static final String HISTORY_VIEW_LIST   = "_History_List"; //广告图记录列表
	public static final String EDIT   = "_Edit";
	public static final String DISPLAY_IMG_EDIT   = "_Display_Edit";//修改广告图展示方式

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String viewPrefix;

	protected IService<Entity> baseService;
	
	


	/**
	 * 注入baseService
	 *
	 * @param baseService
	 */
	@Autowired
	public void setBaseService(IService<Entity> baseService) {
		this.baseService = baseService;
	}

	/**
	 * 实体类型
	 */
	protected final Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	protected AbstractBaseControler() {
		Type parentType = this.getClass().getGenericSuperclass();
		// 转成参数类型接口
		ParameterizedType paramterType = (ParameterizedType) parentType;
		// 得到泛型类型
		Type[] types = paramterType.getActualTypeArguments();
		// 得到传入泛型的类
		entityClass = (Class<Entity>) types[0];

		setViewPrefix(defaultViewPrefix());
	}

	/**
	 * 为泛型创建对象
	 *
	 * @return
	 */
	protected Entity newEntity() {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
		}
	}

	/**
	 * 获取Controller层RequestMapping中的路径
	 *
	 * @return
	 */
	protected String defaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
		if (requestMapping != null && requestMapping.value().length > 0) {
			currentViewPrefix = requestMapping.value()[0];
		}
		return currentViewPrefix;
	}

	/**
	 * 根据id调用service得到对象
	 *
	 * @param id
	 * @return
	 */
	public Entity get(Model model,Long id) {
		if (StringUtil.isNotEmpty(id)) {
			return baseService.selectById(id);
		} else {
			return newEntity();
		}
	}
	
	public Entity get(Long id) {
		if (StringUtil.isNotEmpty(id)) {
			return baseService.selectById(id);
		} else {
			return newEntity();
		}
	}

	/**
	 * 当前模块 视图的前缀 默认 1、获取当前类头上的@RequestMapping中的value作为前缀
	 */
	public void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	public String getViewPrefix() {
		return viewPrefix;
	}

	/**
	 * 获取视图名称：prefixViewName + "/" + simpleName + suffixName
	 * admin/entity(小写)/entity(驼峰)+ suffixName(_List/_Insert/_Update/_Detail)
	 * admin/platformaccountinfo/platformAccountInfo_List
	 * 
	 * @return
	 */
	public String display(String suffixName) {
		return getViewPrefix().toLowerCase() + "/" + getSimpleName() + suffixName;
	}

	/**
	 * 获取首字母小写的实体类名 ： 比如StudentName-->studentName
	 * 
	 * @return
	 */
	protected String getSimpleName() {
		String simpleName = newEntity().getClass().getSimpleName();
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
	}

	/**
	 * 把查询条件对象的属性值为空字符串（""）的属性赋值为null
	 * 
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 */
	protected Entity changeNullStringToNull(Entity entity) throws IllegalAccessException {
		Field[] fs = entity.getClass().getDeclaredFields();

		for (int i = 0; i < fs.length; i++) {

			Field f = fs[i];

			f.setAccessible(true); // 设置些属性是可以访问的

			Object val = f.get(entity); // 得到此属性的值

			if ("".equals(val)) {
				f.set(entity, null);
			}
		}
		return entity;
	}

	/**
	 * list 运行之前
	 * 
	 * @param model
	 *            数据的载体(可把要传到jsp的数据放入model内)
	 * @param entity
	 *            实体类
	 * @param orderBy
	 *            按某个字段排序如："addTime desc" 如果按默认，就传空字符串""
	 * @throws IllegalAccessException
	 */
	protected void preList(Model model, Entity entity, String orderBy) throws IllegalAccessException {
		// 把查询条件对象的属性值为空字符串（""）的属性赋值为null
		Entity afterHandleEntity = changeNullStringToNull(entity);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<Entity> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));

		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, lists);
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(), afterHandleEntity);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model, Entity entity) throws IllegalAccessException {
		preList(model, entity, "");
		System.out.println(display(VIEW_LIST));
		return display(VIEW_LIST);
	}
	
	/**
	 * viewlist 运行之前
	 * 
	 * @param model
	 *            数据的载体(可把要传到jsp的数据放入model内)
	 * @param entity
	 *            实体类
	 * @param orderBy
	 *            按某个字段排序如："addTime desc" 如果按默认，就传空字符串""
	 * @throws IllegalAccessException
	 */
	protected void preViewList(Model model, Entity entity, String orderBy) throws IllegalAccessException {
		// 把查询条件对象的属性值为空字符串（""）的属性赋值为null
		Entity afterHandleEntity = changeNullStringToNull(entity);
		// 处理分页请求
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<>();
		if (orderBy != null && !orderBy.equals("")) {
			PageHelper.orderBy(orderBy);
		}
		initPage(map, pageNum, pageSize);
		List<Entity> lists = baseService.selectByMap(StringUtil.beanToMap(afterHandleEntity));

		// PageAjax pageAjax = new PageAjax<Entity>(lists);
		PageInfo<Object> pagehelper = initPagehelper(map, lists);
		model.addAttribute("pagehelper", pagehelper);
		model.addAttribute("sf", sf);
		// 回显查询条件
		model.addAttribute(getSimpleName(), afterHandleEntity);
	}

	//客服查看列表
	@RequestMapping(value = "/viewList", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewList(Model model, Entity entity) throws IllegalAccessException {
		preViewList(model, entity, "");
		System.out.println(display(VIEWLIST_LIST));
		return display(VIEWLIST_LIST);
	}

	/**
	 * 根据id查看详情前
	 *
	 * @param model
	 * @param id
	 */
	protected void preDetail(Model model, Long id) {
		Entity entity = get(model,id);
		model.addAttribute(getSimpleName(), entity);
		model.addAttribute("sf", sf);
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
		preDetail(model, id);
		return display(VIEW_DETAIL);
	}

	/**
	 * 新增页面前
	 *
	 * @param model
	 */
	protected void preInsertView(Model model) {
		
	}

	/**
	 * 新增页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "insertView", method = { RequestMethod.GET, RequestMethod.POST })
	public String insertView(Model model) {
		preInsertView(model);
		return display(VIEW_INSERT);
	}
 

	/**
	 * 更新前
	 *
	 * @param model
	 * @param id
	 */
	protected void preUpdateView(Model model, Long id) {
		Entity entity = get(model,id);
		model.addAttribute(getSimpleName(), entity);
	}

	/**
	 * 编辑页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateView/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateView(@PathVariable("id") Long id, Model model) {
		preUpdateView(model, id);
		return display(VIEW_UPDATE);
	}
 

	/**
	 * 删除前
	 *
	 * @param model
	 * @param id
	 */
	protected void preDeleteView(Model model, Long id) {
		Entity entity = get(model,id);
		model.addAttribute(getSimpleName(), entity);
	}
	

	/**
	 * 删除页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteView/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteView(@PathVariable("id") Long id, Model model) {
		preDeleteView(model, id);
		return display(VIEW_DELETE);
	}
	
	
	/**
	 *
	 * @param user 管理员对象
	 * @param entityForAfter 修改后的对象
	 * @param entityForBefore 修改前的对象 
	 * @param count 操作状态  1成功 0失败
	 * @param biztype 业务类型 1 添加  2修改  3删除 5其他
	 * @param id 不管是操作 修改还是 删除的时候id都必须传  如果是增加的可以不传
	 * @param simpleName 标识
	 * @return
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @author liuqh
	 */
	protected LogOperation getLogAdmin(AdminUser user,Entity entityForAfter,
			Entity entityForBefore, int count,int biztype,Long id,String simpleName) throws IllegalAccessException, IOException {
		LogOperation logoperation = new LogOperation();
		logoperation.setBaseid(user.getId());
		logoperation.setUsertype(1); //1管理员  2普通用户
		logoperation.setBiztype(biztype);

		//操作结果
		logoperation.setStatus(count);
		//IP
		System.out.println("这个是登录的IP:"+StringUtil.getIpAddr(request));
		logoperation.setIp(StringUtil.getIpAddr(request));

		//ip城市
		if(foxIP(StringUtil.getIpAddr(request))){
			String city = AddressUtils.getAddrName(StringUtil.getIpAddr(request));
			System.out.println(city+"这个是ip城市");
			logoperation.setIpcity(city);
		}

		//操作时间（因有些实体类中没有"admintime这个字段"，无法获得实体类的时间，所以new Date()）
		logoperation.setOpertime(System.currentTimeMillis());

		//得到实体类的参数  如果是修改,那么entity 就是修改前的参数 ,entity2 就是修改后的参数
		Map<String, Object> param = new HashMap<>();
		if(biztype==3){
			param = getParam(entityForBefore);
		}else{
			param = getParam(entityForAfter);
		}
		//因实体类的id是private的用反射得不到这个属性，所以在插入数据库之后再获取实体类的id
		param.put("id", id);

		if(biztype==1){ //业务类型为添加
			//把操作的参数内容放到remark字段
			logoperation.setOpercontent(JSON.toJSONString(getMaps(param,simpleName)));
		}
		if(biztype==3){ //业务类型为删除
			//把操作的参数内容放到remark字段
			logoperation.setOpercontent(JSON.toJSONString(getMaps(param,simpleName)));
		}
		if(biztype==2){//业务类型为修改
			StringBuilder changedParam = getParam2(entityForAfter, entityForBefore,simpleName);
			logoperation.setOpercontent("此次记录的id为："+id+"\r\n"+changedParam.toString());
		}
		logoperation.setRemark("对"+entityClass.getSimpleName()+"表进行了"+LogOperationConstant.BIZTYPEMAP.get(biztype)+"操作");
		return logoperation;
	}
	
	/**
     * 得到管理员能看懂的数据
     * @param @param param
     * @param @return
     * @return Map<String,Object>
     * @author jiangxueyou
     */
    public Map<String,Object> getMaps(Map<String, Object> param,String X){
        Iterator entries = param.entrySet().iterator();  
        
        Map<String, Object> maps = new HashMap<>();
        Map<String,String> mapdata = LogOperationData_Constant.BUSINESSTYPE.get(X);
        
        while (entries.hasNext()) {  
            Map.Entry entry =  (Entry) entries.next();  
          
            String key = (String) entry.getKey();
            if(!StringUtil.isEmpty(key)){
            	key = mapdata.get(key);
            	if(!StringUtil.isEmpty(key)){
            		Object value = (Object)entry.getValue();  
            		if(!StringUtil.isEmpty(value)){
            			maps.put(key, value);
            		}
            	}
            }
        }
		return maps; 
    }
    
    
    /**
     * 通过反射获取到实体类的所有属性
     * @param @param entity
     * @param @return
     * @param @throws IllegalArgumentException
     * @param @throws IllegalAccessException
     * @return Map<String,Object>
     * @author jiangxueyou
     */
    private Map<String, Object> getParam(Entity entity) throws IllegalArgumentException, IllegalAccessException {

        //得到类对象
        Class entityClass = (Class) entity.getClass();
        
        //得到类中的所有属性集合
        Field[] fs = entityClass.getDeclaredFields();
        int fieldLength = fs.length;
        HashMap<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < fieldLength; i++) {
            Field f = fs[i];
            String fieldName = f.getName();
            if ("errors".equals(fieldName) || "serialVersionUID".equals(fieldName)) {
                continue;
            }

            //设置些属性是可以访问的
            f.setAccessible(true);

            //得到此属性的值
            Object val = f.get(entity);
            if (val instanceof Date) {

                //把时间改为如下格式：yyyy-MM-dd hh:mm:ss
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                paramMap.put(fieldName, formatter.format(val));
            } else {
                paramMap.put(fieldName, val);
            }
        }
        return paramMap;
    }
    
    /**
     * 修改的时候把数据格式化
     * @param @param entityForAfter
     * @param @param entityForBefore
     * @param @param simpleName
     * @param @return
     * @param @throws IllegalArgumentException
     * @param @throws IllegalAccessException
     * @return StringBuilder
     * @author jiangxueyou
     */
	private StringBuilder getParam2(Entity entityForAfter,Entity entityForBefore,String simpleName) throws IllegalArgumentException, IllegalAccessException {

		//得到类对象
		Class entityClass = entityForBefore.getClass();

		//得到类中的所有属性集合
		Field[] fs = entityClass.getDeclaredFields();
		int fieldLength = fs.length;
		StringBuilder changedParam = new StringBuilder();
		int num=1;
		for (int i = 0; i < fieldLength; i++) {
			Field f = fs[i];
			String fieldName = f.getName();
			if ("serialVersionUID".equals(fieldName)) {
				continue;
			}

			//设置些属性是可以访问的
			f.setAccessible(true);

			//得到此属性的修改前的值
			Object valForBefore = f.get(entityForBefore);

			//得到此属性的修改后的值
			Object valForAfter = f.get(entityForAfter);


			if(valForBefore!=null&&valForAfter!=null&&!valForBefore.equals(valForAfter)){
				if (valForBefore instanceof Date) {
					//把时间改为如下格式：yyyy-MM-dd hh:mm:ss
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					valForBefore=formatter.format(valForBefore);
					valForAfter = formatter.format(valForAfter);
				}

				if(simpleName!=null&&!simpleName.equals("")){
					Map<String,String> mapdata = com.ganjiangps.wangdaibus.common.constant.LogOperationData_Constant.BUSINESSTYPE.get(simpleName);
					if(mapdata!=null&&mapdata.size()>0){
						String chineseAnnotation = mapdata.get(fieldName);
						if(chineseAnnotation!=null){
							changedParam.append(num+":把"+chineseAnnotation+"的值从<font color='blue'>"+valForBefore+"</font>----改为----<font color='blue'>"+valForAfter+"</font>").append("\r\n");
							num++;
						}
					}
				}
			}


		}
		return changedParam;
	}

	
	public boolean foxIP(String str){
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		 if (str.matches(regex)) {
			return true;
		 }
		return false;
	}
}
