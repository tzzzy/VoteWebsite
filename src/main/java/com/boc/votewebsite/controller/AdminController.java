package com.boc.votewebsite.controller;

import com.boc.votewebsite.entity.Admin;
import com.boc.votewebsite.entity.NamePassword;
import com.boc.votewebsite.mapper.AdminMapper;
import com.boc.votewebsite.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
/*
管理员登录接口 接受请求body中的参数 用户名和密码
返回找到的管理员的id
没找到返回null
 */
    @PostMapping("/admin")//
    public JSONObject  login(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        String name = jsonParam.get("name").toString();
        String password = jsonParam.get("password").toString();
        result.put("userId",adminService.matchUsernamePassword(name,password));
        return result;
    }
}
