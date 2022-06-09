package com.boc.votewebsite.entity;

import lombok.Data;

@Data
public class VoteList {
    private Integer id;
    private Integer projectId;
    private Integer voteId;
    private String voteName;
    private String type;
    private Integer voterId;
    private Integer score;

    public VoteList(Integer id, Integer projectId, Integer voteId,
                    String voteName, String type, Integer voterId, Integer score) {
        this.id = id;
        this.projectId = projectId;
        this.voteId = voteId;
        this.voteName = voteName;
        this.type = type;
        this.voterId = voterId;
        this.score = score;
    }
}
