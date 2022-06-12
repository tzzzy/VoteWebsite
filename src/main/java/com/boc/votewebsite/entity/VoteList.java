package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteList implements Serializable {
    private Integer id;
    private Integer projectId;
    private String voteId;
    private String voteName;
    private String type;
    private String voterId;
    private Integer score;

    public VoteList(Integer id, Integer projectId, String voteId,
                    String voteName, String type, String voterId, Integer score) {
        this.id = id;
        this.projectId = projectId;
        this.voteId = voteId;
        this.voteName = voteName;
        this.type = type;
        this.voterId = voterId;
        this.score = score;
    }
}
