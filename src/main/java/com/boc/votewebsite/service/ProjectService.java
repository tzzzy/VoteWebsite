package com.boc.votewebsite.service;


import com.boc.votewebsite.entity.Project;
import com.boc.votewebsite.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    public List<Project> findByIdAndTime(Integer id, Timestamp time){
        return projectMapper.findByIdAndTime(id, time);
    }
}
