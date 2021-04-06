package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    /**
     * 进入前台登陆界面
     * @return
     */
    @RequestMapping("/")
    public String login(){
        return "login";
    }
    /**
     * 进入后台登陆界面
     * @return
     */
    @RequestMapping("/bg")
    public String bgLogin(){
        return "bgLogin";
    }
}
