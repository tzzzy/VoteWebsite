package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.service.ProjectService;
import com.boc.votewebsite.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class VoteController {
    @Autowired
    private VoteService voteService;
    @Autowired
    private ProjectService projectService;

/*
通过投票人的ID和项目ID来获取被投票人的集合，
其中包括被投票人的ID和分数，
分数可以用来确认是否已经投过票
 */
    @GetMapping("/vote-list")
    public JSONObject getVoteList(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        Integer projectId;
        Integer voteId;
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        try {
            projectId = Integer.parseInt(jsonParam.get("projectId").toString());
            voteId = Integer.parseInt(jsonParam.get("voteId").toString());

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入项目ID或者用户ID错误");
            e.printStackTrace();
            return result;
        }
        List<Project> projectList = projectService.findByIdAndTime(projectId, time);
        Object voteList = voteService.getVoteList(projectId, voteId);
        try {
            if(voteList == null || projectList.size() == 0){
                result.put("return_code", "9999");
                result.put("return_msg", "该项目不在开放期间或该项目不存在");
                return result;
            }
            result.put("return_code", "0");
            result.put("return_msg", "查找成功");
            result.put("data", voteList);
        }catch (Exception e){
        result.put("return_code","-1");
        result.put("return_msg", "方法异常,请重试!");
        e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/vote")
    public JSONObject Vote(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        Integer score;
        Integer projectId;
        Integer voteId;
        Integer voterId;
        try {
            score = Integer.parseInt(jsonParam.get("score").toString());
            projectId = Integer.parseInt(jsonParam.get("projectId").toString());
            voteId = Integer.parseInt(jsonParam.get("voteId").toString());
            voterId = Integer.parseInt(jsonParam.get("voterId").toString());

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入数据错误");
            e.printStackTrace();
            return result;
        }
        Integer lines = voteService.vote(score,projectId,voteId,voterId);
        if(lines > 0){
            result.put("return_code", "0");
            result.put("return_msg", "投票成功");
            result.put("data", "共有"+ lines+ "条数据更新");
        }
        return  result;
    }
}
