package com.example.travels.dao;

import com.example.travels.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.jws.soap.SOAPBinding;

@Mapper
public interface UserDAO {

    //用来保证用户名不重复
    User findByUsername(String username);

    void save(User user);
}
