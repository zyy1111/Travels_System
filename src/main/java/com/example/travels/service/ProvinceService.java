package com.example.travels.service;

import com.example.travels.entity.Province;

import java.util.List;

public interface ProvinceService {
    //参数1：当前页 参数2：每页显示记录数
    List<Province> findByPage(Integer page, Integer rows);

    //查询总条数
    Integer findTotals();

    //添加省份
    void save(Province province); //从前台传过来的数据

    void delete(String id);

    Province findOne(String id);

    void update(Province province);
}
