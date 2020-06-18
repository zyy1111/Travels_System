package com.example.travels.dao;

import com.example.travels.entity.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDAO<T, K> { //泛型：T：当前操作的类型（User,province..)，K：数据库主键的类型(id..)
    void save(T t);
    void update(T t);
    void delete(K k);
    T findOne(K k);
    List<T> findAll();
    List<T> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    Integer findTotals();
}
