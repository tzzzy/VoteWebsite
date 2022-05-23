package com.boc.votewebsite.entity;

import lombok.Data;

@Data
public class Vote {
    private Integer id;
    private Integer projectId;
    private Integer voteId;
    private String description;
    private Integer voterId;
    private Integer score;

    public Vote(Integer id, Integer projectId, Integer voteId, String description, Integer voterId, Integer score) {
        this.id = id;
        this.projectId = projectId;
        this.voteId = voteId;
        this.description = description;
        this.voterId = voterId;
        this.score = score;
    }
}
