package com.example.demo.controll;

import com.example.demo.bean.user;
import com.example.demo.mapper.userMapper;
import com.example.demo.util.DM5utils;
import com.example.demo.util.JwtUtils;
import com.sun.java.browser.plugin2.liveconnect.v1.Result;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class Controlls {
    private static String token="";

    @Autowired
    userMapper  userMapper;
    @Autowired
    JwtUtils jwtUtils;
//    @RequestParam("phone")String phone,  @Length(min = 2,max = 12,message = "密码格式不对")@RequestParam("password")String password
    @PostMapping("/register")
    public String a( @RequestParam("phone")String phone,   @Length(min = 2,max = 12,message = "密码格式不对")@RequestParam("password")String password, HttpServletRequest request
    ){
        if (userMapper.getUser(phone)==null){
            userMapper.insertUser(phone, DM5utils.code(password));
            return "/toLogin";
            //插入一条用户数据
            //跳转到登录页
        }
        else{
            request.setAttribute("msg1","该手机已经注册过了");
            System.out.println("注册失败");
            return "/toRegister";

            //返回注册页提示手机号已经注册过了
        }
       // return userMapper.getUser(phone);
    }

    @RequestMapping("/login")
    public String b(@RequestParam(value = "phone")String phone,@RequestParam(value = "password")String password, HttpServletRequest request
                    ){
        if( userMapper.getUser(phone)!=null &&userMapper.getUser(phone).getPassword().equals(DM5utils.code(password))){
            //成功了，返回当前登录的用户信息，  根据用户信息生成相应的Token，并将Token和用户信息一起返回
            token = jwtUtils.creatjwt(phone,password);
            //session.setAttribute("msg2",userMapper.getUser(phone));
            System.out.println(token);
            return "/toMain";
//            return new Result(Result.SUCCESS(), token);
        }
        else{
            request.setAttribute("msg3","电话或密码错误");
            System.out.println("登录失败");
            return "/toLogin";
        }
    }

    @RequestMapping("/changePW")
    public String c(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword){
        if(token.equals("")){
            return "/toLogin";
        }
        //  判断旧密码是否正确，正确就改变密码，错误就提示错误 ，判断是否登录了
        else {
            Claims claims = jwtUtils.parseJwt(token);
        if(oldPassword.equals(claims.get("password"))){
            //更新密码
            userMapper.updatePassword((String) claims.get("phone"),DM5utils.code(newPassword));
            System.out.println("改密码成功");
            return "/toMain";
        }
        else {
            //旧秘密错误
            System.out.println("旧密码错误");
        }
        return "/toMain";
        }
    }
    //DM5 不晓得能不能解密，不能的话怎么返回密码？
//    @RequestMapping("/findPW")
//    public String e(){
//    }
    @ResponseBody
    @RequestMapping("/findUser")
    public user f(@RequestParam("phone")String phone){
            user user = userMapper.findUserMessage(phone);
        return user;
    }


    @GetMapping("/getUserMessage")
   public String d(HttpServletRequest request){
        Claims claims = jwtUtils.parseJwt(token);
        user user = new user();
        user.setPhone((String) claims.get("phone"));
        user.setPassword((String)claims.get("password"));
        request.setAttribute("userMessage",user);
    return "/toMain";
    }



    @ResponseBody
    @RequestMapping("/toLogin")
    public String ToLogin(){
        return "这是登录页面";
    }
    @ResponseBody
    @RequestMapping("/toRegister")
    public String ToRegister(){
        return "这是注册页面";
    }
    @ResponseBody
    @RequestMapping("/toMain")
    public String ToMain(){
        return "这是主页面";
    }

}
//简要描述
//
//
//
//请求url
//
//
//请求方式
//
//参数
//
//
//
//返回参数说明
//
//
//
//














