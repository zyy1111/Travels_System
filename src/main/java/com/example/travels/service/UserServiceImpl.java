package com.example.travels.service;

import com.example.travels.dao.UserDAO;
import com.example.travels.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;


    @Override
    public User login(User user) {
        User userDB = userDAO.findByUsername(user.getUsername());
        if(userDB != null) {
            if (userDB.getPassword().equals(user.getPassword()))  return userDB;
            throw new RuntimeException("Wrong password!");
        } else {
            throw new RuntimeException("Wrong username!");
        }
    }

    @Override
    public void register(User user) {
        if (userDAO.findByUsername(user.getUsername()) == null) {
            userDAO.save(user);
        } else {
            throw new RuntimeException("username already exists!");
        }
    }
}
