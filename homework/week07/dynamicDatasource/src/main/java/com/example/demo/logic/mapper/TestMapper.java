package com.example.demo.logic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface TestMapper {

    Map getById(Long id);

    Boolean insert(@Param("data") Map data);

}
