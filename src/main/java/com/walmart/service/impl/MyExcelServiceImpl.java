package com.walmart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.dao.MyExcelMapper;
import com.walmart.model.MyExcel;
import com.walmart.service.MyExcelService;

@Service
public class MyExcelServiceImpl implements MyExcelService{
	
	@Autowired
	MyExcelMapper myExcelMapper;
	
	public int deleteByPrimaryKey(Long id) {
		return myExcelMapper.deleteByPrimaryKey(id);
	}

	public int insert(MyExcel record) {
		return myExcelMapper.insert(record);
	}

	public int insertSelective(MyExcel record) {
		return myExcelMapper.insertSelective(record);
	}

	public MyExcel selectByPrimaryKey(Long id) {
		return myExcelMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(MyExcel record) {
		return myExcelMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(MyExcel record) {
		return myExcelMapper.updateByPrimaryKey(record);
	}

	public List<MyExcel> selectAll() {
		return myExcelMapper.selectAll();
	}

}
