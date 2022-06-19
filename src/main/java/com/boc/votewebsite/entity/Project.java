package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Project implements Serializable {
    private Integer projectID;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Integer season;
    private  Integer year;
}
