package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Vote implements Serializable {

    private Integer projectId;
    private String voteId;
    private String type;
    private String voterId;
    private Double score;

    public Vote(Integer projectId, String voteId, String type, String voterId, Double score) {
        this.projectId = projectId;
        this.voteId = voteId;
        this.type = type;
        this.voterId = voterId;
        this.score = score;
    }
}
