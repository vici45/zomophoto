package com.zomo.photo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class HomeController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "user/login2";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "user/register";
    }

    @RequestMapping(value = "/file",method = RequestMethod.GET)
    public String file(){
        return "file";
    }

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String admin(){
        return "admin/admin-index";
    }

    @RequestMapping(value = "/addProjectPage",method = RequestMethod.GET)
    public String addProject(){
        return "admin/addProject";
    }



}
