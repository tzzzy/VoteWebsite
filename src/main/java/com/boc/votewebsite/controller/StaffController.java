package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.service.StaffService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;

    //获取所有的用户
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

    @GetMapping("/staff_export")
    public JSONObject getAllStaffExport(){
        JSONObject result = new JSONObject();
        List<StaffExport> staffs = staffService.findAllExport();
        if(staffs.size()==0){
            result.put("return_code", "9999");
            result.put("return_msg", "没有用户，请添加");
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "导出数据成功");
        result.put("data",staffs);
        return result;
    }

    @PostMapping("/staff-update")
    public JSONObject updateStaff(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String id = jsonParam.get("id").toString();
        String name = jsonParam.get("name").toString();
        String institution = jsonParam.get("institution").toString();
        String position = jsonParam.get("position").toString();
        char type = jsonParam.get("type").toString().charAt(0);
        if(name == null || institution == null || position == null || id == null){
            result.put("return_code", "9999");
            result.put("return_msg", "缺少参数，请重试");
            return  result;
        }
        Integer re = staffService.updateById(id, institution,type,name,position);
        if(re != 1){
            result.put("return_code", "9999");
            result.put("return_msg", "更新失败，请重试");
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "更新成功");
        return result;
    }

    @PostMapping("/staff-delete")
    public JSONObject deleteStaff(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String id = jsonParam.get("id").toString();
        if(id == null){
            result.put("return_code", "9999");
            result.put("return_msg", "缺少员工ID，请重试");
            return  result;
        }
        Integer re = staffService.deleteById(id);
        if(re != 1){
            result.put("return_code", "9999");
            result.put("return_msg", id + "员工不存在，请重试");
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "删除成功");
        return result;
    }


}
