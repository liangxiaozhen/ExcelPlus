package com.ganjiangps.wangdaibus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.activerecord.Model;
import com.ganjiangps.wangdaibus.common.constant.LogOperationConstant;
import com.ganjiangps.wangdaibus.common.constant.LogOperationData_Constant;
import com.ganjiangps.wangdaibus.common.controller.AbstractBaseControler;
import com.ganjiangps.wangdaibus.common.response.AjaxResult;
import com.ganjiangps.wangdaibus.common.response.Meta;
import com.ganjiangps.wangdaibus.common.util.AddressUtils;
import com.ganjiangps.wangdaibus.common.util.StringUtil;
import com.ganjiangps.wangdaibus.model.LogOperation;
import com.ganjiangps.wangdaibus.model.SuperEntity;
import com.ganjiangps.wangdaibus.service.LogOperationService;

/**
 * 
* @ClassName: AbstractBaseAdminController 
* @Description: TODO(admin 通用controller) 
* @author cjm
* @date 2018年4月25日 下午2:17:02 
* 
* 注：这里只实现
* 	  保存，修改，删除操作
* 
* @param <Entity>
 */
public abstract class AbstractBaseAdminController<Entity> extends AbstractBaseControler<Entity>  {
	
	@Resource
    protected LogOperationService logOperationService;

	/**
	 * 新增前(子类可重写此方法做保存前对字段进行后台验证)
	 * 
	 * @param entity
	 */
	protected boolean preInsert(AjaxResult ajaxResult,Entity entity) {
 		//这里使用了Hibernate-validate 进行验证  如何实体类属性没有加注解则不会验证
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator  validator = factory.getValidator();
		Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(entity);
 		if(constraintViolations.iterator().hasNext() && constraintViolations.iterator().next().getMessage() != null){
 			Meta meta = new Meta();
  			meta.setCode(AjaxResult.FAIL_CODE);
  			meta.setMessage(constraintViolations.iterator().next().getMessage());
  			ajaxResult.setMeta(meta); 
			 return false;
		}
		return true;
	}
	
	/**
	 * 新增
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult insert(Entity entity) {
		AjaxResult ajaxResult = AjaxResult.createAjaxResult();
 		if (!preInsert(ajaxResult,entity)) {
			return ajaxResult;
		}

		try {

			boolean flag = baseService.insert(entity);
			if (flag) {
				/*logOperationService.insert(getLogAdmin(1,entity, null,1,LogOperationConstant.BIZTYPE_ADD,null,getSimpleName()));*/
				return ajaxResult.successAjaxResult("保存成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ajaxResult.failAjaxResult("因网络响应不及时,操作失败,请联系客服或重新操作");
		}
		return ajaxResult.failAjaxResult("保存失败");
	}

	/**
	 * 更新前(子类可重写此方法做更新前对字段进行后台验证)
	 * 
	 * @param entity
	 */
	protected boolean preUpdate(AjaxResult ajaxResult,Entity entity) {
 		//这里使用了Hibernate-validate 进行验证 
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator  validator = factory.getValidator();
		Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(entity);
 		if(constraintViolations.iterator().hasNext() && constraintViolations.iterator().next().getMessage() != null){
  			Meta meta = new Meta();
  			meta.setCode(AjaxResult.FAIL_CODE);
  			meta.setMessage(constraintViolations.iterator().next().getMessage());
  			ajaxResult.setMeta(meta); 
			return false;
		}
 		session.setAttribute("beforeEntity", entity);
		return true;
	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult update(Entity entity) {
		System.out.println("=================1212");
		AjaxResult ajaxResult = AjaxResult.createAjaxResult();
		if(!preUpdate(ajaxResult,entity)){
			return ajaxResult;
		};
		
		System.out.println("=================");
	 
		try {
			Entity entity2 = (Entity) session.getAttribute("beforeEntity"); //获取到修改之前的对象
			boolean flag = baseService.updateById(entity);
			if (flag) {
			/*	logOperationService.insert(getLogAdmin(1,entity, entity2,1,LogOperationConstant.BIZTYPE_UPDATE,null,getSimpleName()));*/
				return ajaxResult.successAjaxResult("更新成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxResult.failAjaxResult("因网络响应不及时,操作失败,请联系客服或重新操作");
		}
		return ajaxResult.failAjaxResult("更新失败");
	}
	
 

	/**
	 * 删除前(子类可重写此方法做删除前的一些逻辑处理：如启用中或已被引用了就不准删除)
	 * 
	 * @param map
	 */
	protected boolean preDelete(AjaxResult ajaxResult,Long id) {
 		if(id == null){
 			Meta meta = new Meta();
  			meta.setCode(AjaxResult.FAIL_CODE);
  			meta.setMessage("id 不能为空");
  			ajaxResult.setMeta(meta); 
 			return false;
		}
 		Entity entity = get(id);
 		session.setAttribute("beforedeleteEntity", entity);
		return true;
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public AjaxResult delete(Long id) {
		AjaxResult ajaxResult = AjaxResult.createAjaxResult();
 		if (!preDelete(ajaxResult,id)) {
			return ajaxResult;
		}
		
		try {
			boolean flag = baseService.deleteById(id);
			Entity entity = (Entity) session.getAttribute("beforedeleteEntity"); //获取到修改之前的对象
			if (flag) {
				/*logOperationService.insert(getLogAdmin(1,entity, null,1,LogOperationConstant.BIZTYPE_DELETE,id,getSimpleName()));*/
				return ajaxResult.successAjaxResult("删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxResult.failAjaxResult("因网络响应不及时,操作失败,请联系客服或重新操作");
		}
		return ajaxResult.failAjaxResult("删除失败");
	}
	
	
	
}
