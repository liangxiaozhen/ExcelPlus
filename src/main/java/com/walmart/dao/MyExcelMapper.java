package com.walmart.dao;

import java.util.List;

import com.walmart.model.MyExcel;


public interface MyExcelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyExcel record);

    int insertSelective(MyExcel record);

    MyExcel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyExcel record);

    int updateByPrimaryKey(MyExcel record);
    
    List<MyExcel> selectAll();
}