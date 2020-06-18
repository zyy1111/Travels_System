package com.example.travels.service;

import com.example.travels.entity.User;

public interface UserService {

    User login(User user);

    void register(User user);
}
