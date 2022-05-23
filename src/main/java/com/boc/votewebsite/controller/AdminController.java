package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
/*
管理员登录接口 接受请求body中的参数 用户名和密码
返回找到的管理员的id
没找到返回错误
 */
    @GetMapping("/admin")
    public JSONObject  login(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        String name = jsonParam.get("name").toString();
        String password = jsonParam.get("password").toString();
        if (null == name ||null == password) {
            result.put("return_code","9999");
            result.put("return_msg", "传入用户名或密码为空");
            return result;
        }
        Object userID =  adminService.matchUsernamePassword(name,password);
        try {
            if (null == userID) {
                result.put("return_code", "9999");
                result.put("return_msg", "账号或密码错误");
                return  result;
            }
            result.put("return_code", "0");
            result.put("return_msg", "登录成功");
            result.put("data", userID);
        }catch (Exception e){
            result.put("return_code","-1");
            result.put("return_msg", "方法异常,请重试!");
            e.printStackTrace();
        }
        return result;
    }
}
