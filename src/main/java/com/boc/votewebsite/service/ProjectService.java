package com.boc.votewebsite.service;


import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.mapper.ProjectMapper;
import com.boc.votewebsite.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;


    public List<Project> findByTime(Timestamp time){
        return projectMapper.findByTime(time);
    }

    public List<Project> findBySeasonAndYear(Integer season, Integer year){
        return projectMapper.findBySeasonAndYear(season, year);
    }

    public Integer addProject(Timestamp startTime, Timestamp endTime, Timestamp createTime, Integer season, Integer year){
        //2022年第一季度的项目id就是20221
        Integer projectId = year*10+season;
        return projectMapper.addProject(projectId, startTime,endTime, createTime, season, year);
    }

    public Integer deleteProject(Integer projectId){
        return projectMapper.deleteProject(projectId);
    }

    public List<Project> findByYear(Integer year){
        return projectMapper.findByYear(year);
    }
}
