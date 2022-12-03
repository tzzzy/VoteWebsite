package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.*;
import com.boc.votewebsite.service.ProjectService;
import com.boc.votewebsite.service.ResultService;
import com.boc.votewebsite.service.StaffService;
import com.boc.votewebsite.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.randomInt;
import static cn.hutool.core.util.RandomUtil.randomStringUpper;

@RestController
@CrossOrigin
public class ProjectController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ResultService resultService;


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
            result.put("return_msg", year+"年第"+season+"季度的项目已存在");
            return result;
        }
        //将上一个项目在VOTE表中的数据更新到RESULT中
        Integer latestProjectId = projectService.findLatest();
        if(latestProjectId != null){
            Integer resultNumber = 0;
            List<VoteResult> aResult = voteService.findByProjectIdAndStaffType(latestProjectId,'A');
            List<VoteResult> bResult = voteService.findByProjectIdAndStaffType(latestProjectId,'B');
            resultNumber += resultService.addResult(latestProjectId, aResult);
            resultNumber += resultService.addResult(latestProjectId, bResult);
            if(resultNumber == 0){
                result.put("return_code", "9999");
                result.put("return_msg", "更新分数失败！");
                return result;
            }
            System.out.print("add results from project:" +latestProjectId+",with "+resultNumber +"records");
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
        //更新密码
        List<StaffExport> staffs= staffService.findAllExport();
        //创建足够用的不同的密码
        Set<String> passwords = new HashSet<String>();
        while(passwords.size() < staffs.size()){
            String pwd = randomStringUpper(12);
            passwords.add(pwd);
        }
        Iterator iterator = passwords.iterator();
        for(int i = 0;i < staffs.size(); i++){
            //Integer password = randomInt(100000,999999); 用不到了
            Integer updateRe = staffService.updatePasswordByStaffId(staffs.get(i).getStaff_id(),iterator.next().toString());
            if(updateRe==0){
                result.put("return_code", "9999");
                result.put("return_msg", "创建密码失败，请重试" + voteService.deleteVotes(projectId).toString());
                result.put("data", projectService.deleteProject(projectId));
                return result;
            }
        }
        result.put("return_code", "0 ");
        result.put("return_msg", "创建项目成功");
        return  result;
    }
}
