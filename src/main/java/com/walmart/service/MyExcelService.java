package com.walmart.service;

import java.util.List;

import com.walmart.model.MyExcel;

public interface MyExcelService {
	
		int deleteByPrimaryKey(Long id);

	    int insert(MyExcel record);

	    int insertSelective(MyExcel record);

	    MyExcel selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(MyExcel record);

	    int updateByPrimaryKey(MyExcel record);
	    
	    List<MyExcel> selectAll();
}
