package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffIdType;
import com.boc.votewebsite.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private StaffService staffService;
/*
管理员登录接口 接受请求body中的参数 用户名和密码
返回找到的管理员的id
没找到返回错误
 */
    @PostMapping("/login")
    public JSONObject  login(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        String id;
        if (null == jsonParam.get("id").toString()||null == jsonParam.get("password").toString()) {
            result.put("return_code","9999");
            result.put("return_msg", "传入用ID或密码为空");
            return result;
        }
        try {
            id = jsonParam.get("id").toString();

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入用户ID错误");
            e.printStackTrace();
            return result;
        }
        String password = jsonParam.get("password").toString();
        List<Staff> user =  staffService.matchPassword(password);//用密码就可以检索到用户Id,密码所有用户都不同
        try {
            if (user.size() == 0) {
                result.put("return_code", "9999");
                result.put("return_msg", "密码不存在");
                return  result;
            }
            StaffIdType data = new StaffIdType(user.get(0).getStaff_id(), user.get(0).getType());
            result.put("return_code", "0");
            result.put("return_msg", "登录成功");
            result.put("data", data);
        }catch (Exception e){
            result.put("return_code","-1");
            result.put("return_msg", "方法异常,请重试!");
            e.printStackTrace();
        }
        return result;
    }
}
