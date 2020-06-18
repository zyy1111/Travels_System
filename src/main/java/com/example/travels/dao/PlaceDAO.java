package com.example.travels.dao;

import com.example.travels.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaceDAO extends BaseDAO<Place, String> {
    //多个参数传值一定要加注解@Param
    List<Place> findByProvinceIdPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("provinceId") String provinceId);
    Integer findByProvinceIdCounts(String provinceId);
}
