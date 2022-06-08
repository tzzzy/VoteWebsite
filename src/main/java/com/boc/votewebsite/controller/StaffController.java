package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.service.StaffService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/staff")
    public JSONObject getAllStaff(){
        JSONObject result = new JSONObject();
        if(staffService.findAllStaff().size()==0){
            result.put("return_code", "9999");
            result.put("return_msg", "没有用户，请添加");
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "登录成功");
        result.put("data",staffService.findAllStaff());
        return result;
    }

    @PostMapping("/staff_name")
    public JSONObject getStaffByName(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String name = jsonParam.get("name").toString();
        if(null == name){
            result.put("return_code","9999");
            result.put("return_msg", "用户名为空");
            return result;
        }
        if(staffService.findByStaffName(name).size()==0){
            result.put("return_code","9999");
            result.put("return_msg", "找不到"+name);
            return result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "查找成功");
        result.put("data", staffService.findByStaffName(name));
        return  result;
    }
}
