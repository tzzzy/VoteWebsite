package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class InstitutionController {
    @Autowired
    private InstitutionService institutionService;

    @GetMapping("/institution")
    JSONObject getAllInstitution(){
        JSONObject result = new JSONObject();
        if(institutionService.findAll().size()==0){
            result.put("return_code", "9999");
            result.put("return_msg", "没有机构，请添加");
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "登录成功");
        result.put("data",institutionService.findAll());
        return result;
    }
}
