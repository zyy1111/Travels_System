package com.example.travels.controller;

import com.example.travels.entity.Result;
import com.example.travels.entity.User;
import com.example.travels.service.UserService;
import com.example.travels.utils.CreateImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController   //把原来的controller转为restcontroller,自动将对象转为jason,不用再类上写@responsebody
@RequestMapping("user")
@CrossOrigin//允许跨域
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("register")
    public Result register(String code, String key, @RequestBody User user, HttpServletRequest request){
        Result result = new Result();
        log.info("接收的验证码"+code);
        log.info("接收的user对象"+user);
        log.info("接收的key"+key);
        //验证码
        String keyCode = (String) request.getServletContext().getAttribute(key);
        log.info(keyCode);
        try{
            if(code.equalsIgnoreCase(keyCode)){
                //注册用户
                userService.register(user);
                result.setMsg("注册成功！");
            }else{
                throw new RuntimeException("验证码有误！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return  result;
    }

    @RequestMapping("login")
    public Result login(@RequestBody User user, HttpServletRequest request) {
        Result result = new Result();
        log.info("user: " + user);
        try {
            User userDB = userService.login(user);
            //登陆成功时候保存用户的标记 redis(目前先存在ServletContext中)
            request.getServletContext().setAttribute(userDB.getId(), userDB);
            result.setMsg("Successful login!").setUserId(userDB.getId());
        } catch (Exception e) {
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }


    @GetMapping("getImage")
    public Map<String, String> getImage(HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, String> result = new HashMap<>();
        CreateImageCode createImageCode = new CreateImageCode();
        //1.获取验证码
        String securityCode = createImageCode.getCode();
        //2.验证码存入session
        String key =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        session.getServletContext().setAttribute(key,securityCode);
        //3.生成图片
        BufferedImage image = createImageCode.getBuffImg();
        //4.进行Base64编码
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        ImageIO.write(image,"png",bos);
        String string = Base64Utils.encodeToString(bos.toByteArray());
        result.put("key",key);
        result.put("image",string);
        return result;
    }
}
