package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private Integer projectId;
    private String staffId;
    private Double equal;
    private Double supSub;
    private Double average;

    public Result(Integer projectId, String staffId, Double equal, Double supSub, Double average) {
        this.projectId = projectId;
        this.staffId = staffId;
        this.equal = equal;
        this.supSub = supSub;
        this.average = average;
    }
}
