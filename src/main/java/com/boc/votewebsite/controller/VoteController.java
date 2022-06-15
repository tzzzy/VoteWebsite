package com.boc.votewebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.entity.VoteList;
import com.boc.votewebsite.service.ProjectService;
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
        try {
            List<VoteList> voteList = voteService.getVoteList(projectList.get(0).getProjectID(), voterId,type);
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
}
