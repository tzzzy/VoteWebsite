package com.boc.votewebsite.entity;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class Project {
    private Integer projectID;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
}
