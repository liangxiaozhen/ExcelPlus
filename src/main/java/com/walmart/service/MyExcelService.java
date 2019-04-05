package com.walmart.service;

import com.walmart.model.MyExcel;

public interface MyExcelService {
	
		int deleteByPrimaryKey(Long id);

	    int insert(MyExcel record);

	    int insertSelective(MyExcel record);

	    MyExcel selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(MyExcel record);

	    int updateByPrimaryKey(MyExcel record);

}
