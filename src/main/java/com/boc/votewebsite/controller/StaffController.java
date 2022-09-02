package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.service.ProjectService;
import com.boc.votewebsite.service.StaffService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private ProjectService projectService;

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
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        List<Project> projectList = projectService.findByTime(time);
        if(projectList.size() == 0){
            result.put("return_code", "9999");
            result.put("return_msg", "在项目开放时间内无法进行员工信息修改");
            return result;
        }
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

    @PostMapping("/staff-import")
    public JSONObject importStaff(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        List<Map> staffs = (List<Map>) jsonParam.get("staffs");
        Integer add = 0;
        Integer update = 0;
        for(int i = 0; i < staffs.size(); i++){
            Integer exist = staffService.findById(staffs.get(i).get("id").toString()).size();
            String id = staffs.get(i).get("id").toString();
            String institution = staffs.get(i).get("institution").toString();
            char type = staffs.get(i).get("type").toString().charAt(0);
            String name = staffs.get(i).get("name").toString();
            String position = staffs.get(i).get("position").toString();
            if(exist > 0){
                Integer updateResult = staffService.updateById(id,institution,type, name,position);
                update += updateResult;
            }
            else {
                Integer re = staffService.addStaff(id, institution, type, name, position);
                add += re;
            }
        }
        result.put("return_code", "0");
        result.put("return_msg", "导入用户" + staffs.size() + "人,创建成功"+ add + "人,更新成功"+ update + "人。");
        return result;
    }

    @PostMapping("/create-c")
    public JSONObject createTypeC(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        Integer add = 0;
        Integer delete = 0;
        List<Map> ins = (List<Map>) jsonParam.get("institutions");
        Map<String, Integer> currentCAmount = staffService.findCAmount();
        for(int i = 0; i< ins.size(); i++){
            if(currentCAmount.containsKey(ins.get(i).get("institution"))){
                Integer amountOld = currentCAmount.get(ins.get(i).get("institution"));
                Integer amountNew = Integer.valueOf(ins.get(i).get("amount").toString());
                if(amountNew > amountOld){
                    add += staffService.addCType(ins.get(i).get("institution").toString(),amountNew ,amountOld);
                }
                else if(amountNew.equals(amountOld)){
                    continue;
                }
                else {
                    delete += staffService.deleteCType(ins.get(i).get("institution").toString(),amountNew ,amountOld);
                }

            }
            else{
                add += staffService.addCType(ins.get(i).get("institution").toString(), Integer.valueOf(ins.get(i).get("amount").toString()),0);
            }
        }
        result.put("return_code", "0");
        result.put("return_msg", "创建C类用户" + add + "人,删除C类用户" + delete + "人。");
        return  result;
    }
}
