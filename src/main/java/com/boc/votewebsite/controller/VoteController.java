package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.entity.VoteList;
import com.boc.votewebsite.entity.VoteProgress;
import com.boc.votewebsite.entity.VoteResult;
import com.boc.votewebsite.service.ProjectService;
import com.boc.votewebsite.service.ResultService;
import com.boc.votewebsite.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class VoteController {
    @Autowired
    private VoteService voteService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ResultService resultService;

/*
通过投票人的ID和项目ID来获取被投票人的集合，
其中包括被投票人的ID和分数，
分数可以用来确认是否已经投过票
 */
    @PostMapping("/vote-list")
    public JSONObject getVoteList(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        String voterId;
        String type;
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        try {
            voterId = jsonParam.get("voterId").toString();
            type = jsonParam.get("type").toString();

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入用户ID错误");
            e.printStackTrace();
            return result;
        }
        List<Project> projectList = projectService.findByTime(time);
        if(projectList.size()==0)
        {
            result.put("return_code", "9999");
            result.put("return_msg", "该项目不在开放期间或该项目不存在");
            return result;
        }
        try {
            List<VoteList> voteList = voteService.getVoteList(projectList.get(0).getProjectID(), voterId,type);
            if(voteList == null || projectList == null){
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
/*
    由项目ID，评分人ID个被评分人ID来确认评分的条目，评分成功返回受影响的行数也就是数字1
*/
    @PostMapping("/vote")
    public JSONObject vote(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        Double score;
        Integer projectId;
        String voteId;
        String voterId;
        try {
            score = Double.parseDouble(jsonParam.get("score").toString());
            projectId = Integer.parseInt(jsonParam.get("projectId").toString());
            voteId = jsonParam.get("voteId").toString();
            voterId = jsonParam.get("voterId").toString();

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入数据错误");
            e.printStackTrace();
            return result;
        }
        if(score > 100){
            score = 100.00;
        }//防止错误
        Integer lines = voteService.vote(score,projectId,voteId,voterId);
        if(lines > 0){
            result.put("return_code", "0");
            result.put("return_msg", "投票成功");
            result.put("data", "共有"+ lines+ "条数据更新");
        }
        else{
            result.put("return_code", "9999");
            result.put("return_msg", "投票数据更新失败");
        }
        return  result;
    }

    @PostMapping("/vote-status")
    public JSONObject getVoteStatus(@RequestBody JSONObject jsonParam){
        JSONObject result = new JSONObject();
        Integer projectId;
        String voteId;
        String voterId;
        try {
            projectId = Integer.parseInt(jsonParam.get("projectId").toString());
            voteId = jsonParam.get("voteId").toString();
            voterId = jsonParam.get("voterId").toString();

        } catch (NumberFormatException e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入数据错误");
            e.printStackTrace();
            return result;
        }
        Double score = voteService.getScore(projectId,voteId,voterId);
        if(score >= 0 || score == null){
            result.put("return_code", "0");
            result.put("return_msg", "查找成功");
            result.put("data", score);
        }
        else {
            result.put("return_code", "9999");
            result.put("return_msg", "找不到该投票记录");
        }
        return result;
    }

    @PostMapping("/vote-result")
    public JSONObject getVoteResult(@RequestBody JSONObject jsonParam) {
        JSONObject result = new JSONObject();
        Integer season;
        Integer year;
        char type;
        try {
            season = Integer.parseInt(jsonParam.get("season").toString());
            year = Integer.parseInt(jsonParam.get("year").toString());
            type = jsonParam.get("type").toString().charAt(0);
        } catch (Exception e) {
            result.put("return_code", "9999");
            result.put("return_msg", "传入数据错误");
            e.printStackTrace();
            return result;
        }
        //先查找项目表中是否存在该年份的项目
        List<Project> project = projectService.findByYear(year);
        if(project.size() == 0){
            result.put("return_code", "9999");
            result.put("return_msg", year + "年不存在项目，请重新查找");
            return  result;
        }
        //查找全年结果去RESULT表中查找，当前正在进行的项目不会统计在内
        if(season == 0){
            List<VoteResult> res = resultService.findResultByYearAndType(year, type);
            if(res.size() == 0){
                result.put("return_code", "9999");
                result.put("return_msg", "未找到"+year+"年的结果或该年的项目正在进行中，请重新查找");
                return  result;
            }
            result.put("return_code", "0");
            result.put("return_msg", "查找成功");
            result.put("data", res);
            return  result;
        }
        //查找非全年的项目
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        List<Project> projectList = projectService.findByTime(time);
        if(projectList.size() > 0){//当前有项目开放
            Integer thisyear = projectList.get(0).getYear();
            Integer thisseason = projectList.get(0).getSeason();
            if(thisyear.equals(year)  && thisseason.equals(season)){
                //确定查找的为当前项目，从VOTE表中查找
                List<VoteResult> data = voteService.findByProjectIdAndStaffType(projectList.get(0).getProjectID(),type);
                if(data.size() == 0){
                    result.put("return_code", "9999");
                    result.put("return_msg", "该项目缺少投票表，请联系数据库管理员");
                    return  result;
                }
                result.put("return_code", "0");
                result.put("return_msg", "查找成功");
                result.put("data", data);
                return  result;
            }
            //查找历史项目，从RESULT表中查找
            List<Project> targetProject = projectService.findBySeasonAndYear(season, year);
            List<VoteResult> rdata = resultService.findResultByIdAndType(targetProject.get(0).getProjectID(),type);
            if(rdata.size() == 0){
                result.put("return_code", "9999");
                result.put("return_msg", "该项目信息不在结果表中，请联系数据库管理员");
                return  result;
            }
            result.put("return_code", "0");
            result.put("return_msg", "查找成功");
            result.put("data", rdata);
            return  result;
        }
        //当前没有项目在开放，从RESULT表中查找数据
        Integer targetId = project.get(0).getProjectID();
        List<VoteResult> rdata = resultService.findResultByIdAndType(targetId,type);
        if(rdata.size()==0){//项目刚结束，结果没有从VOTE中更新到RESULT中，会出现找不到信息的情况，更新信息到RESULT表***
            List<VoteResult> rdata2 = voteService.findByProjectIdAndStaffType(targetId,type);
            result.put("return_code", "0");
            result.put("return_msg", "查找成功");
            result.put("data", rdata2);
            return  result;
        }
        result.put("return_code", "0");
        result.put("return_msg", "查找成功");
        result.put("data", rdata);
        return  result;
    }

    @GetMapping("/vote-progress")
    public JSONObject getVoteProgress(){
        JSONObject result = new JSONObject();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        List<Project> projectList = projectService.findByTime(time);
        if(projectList.size() == 0){
            result.put("return_code", "9999");
            result.put("return_msg", "该项目不在开放期间或该项目不存在");
            return result;
        }
        List<VoteProgress> data = voteService.getProgressById(projectList.get(0).getProjectID());
        result.put("return_code", "0");
        result.put("return_msg", "查找成功");
        result.put("data", data);
        return result;
    }
}
