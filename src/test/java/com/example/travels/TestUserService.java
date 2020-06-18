package com.example.travels;

import com.example.travels.entity.User;
import com.example.travels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {
    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("carrie");
        user.setPassword("123");
        user.setEmail("yiyun11@163.com");
        userService.register(user);
    }
}
