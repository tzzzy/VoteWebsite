package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Vote implements Serializable {
    private Integer id;
    private Integer projectId;
    private Integer voteId;
    private String type;
    private Integer voterId;
    private Integer score;

    public Vote(Integer id, Integer projectId, Integer voteId, String type, Integer voterId, Integer score) {
        this.id = id;
        this.projectId = projectId;
        this.voteId = voteId;
        this.type = type;
        this.voterId = voterId;
        this.score = score;
    }
}
