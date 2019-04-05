package com.walmart.dao;

import com.walmart.model.MyExcel;

public interface MyExcelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyExcel record);

    int insertSelective(MyExcel record);

    MyExcel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyExcel record);

    int updateByPrimaryKey(MyExcel record);
}