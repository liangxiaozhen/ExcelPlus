package com.walmart.common;



import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
}	


