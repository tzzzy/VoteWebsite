package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteList implements Serializable {

    private Integer projectId;
    private String voteId;
    private String voteName;
    private String type;
    private String voterId;
    private Double score;

    public VoteList(Integer projectId, String voteId,
                    String voteName, String type, String voterId, Double score) {
        this.projectId = projectId;
        this.voteId = voteId;
        this.voteName = voteName;
        this.type = type;
        this.voterId = voterId;
        this.score = score;
    }
}
