package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffManage;
import com.boc.votewebsite.service.ProjectService;
import com.boc.votewebsite.service.StaffService;
import com.boc.votewebsite.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class ProjectController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create-project")
    public JSONObject createProject(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        Integer season;
        Integer year;
        Timestamp startTime;
        Timestamp endTime;
        Date date = new Date();
        Timestamp createTime = new Timestamp(date.getTime());
        try{
            season = Integer.parseInt(jsonParam.get("season").toString());
            year = Integer.parseInt(jsonParam.get("year").toString());
            startTime = Timestamp.valueOf(jsonParam.get("startTime").toString());
            endTime = Timestamp.valueOf(jsonParam.get("endTime").toString());

        } catch (Exception e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入的数据格式错误");
            e.printStackTrace();
            return result;
        }
        //如果该项目已存在就不用创建
        List<Project> pro = projectService.findBySeasonAndYear(season, year);
        if(pro.size() > 0){
            result.put("return_code", "9999");
            result.put("return_msg", year.toString()+"年第"+season.toString()+"季度的项目已存在");
            return result;
        }
        //创建项目表中的记录
        Integer re = projectService.addProject(startTime, endTime, createTime, season, year);
        if(re != 1){
            result.put("return_code", "9999");
            result.put("return_msg", "创建项目失败，请重试");
            return result;
        }
        //插入创建投票表
        Integer projectId = projectService.findBySeasonAndYear(season, year).get(0).getProjectID();
        Integer createResult = voteService.addVotes(projectId);
        if(createResult == 0){
            result.put("return_code", "9999");
            result.put("return_msg", "更新投票表失败，请重试" + voteService.deleteVotes(projectId).toString());
            result.put("data", projectService.deleteProject(projectId));
            return result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "创建项目成功");
        return  result;
    }
}
