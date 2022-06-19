package com.boc.votewebsite.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoteResult implements Serializable {
    private  String voteId;
    private String name;
    private char staffType;
    private String institutionId;
    private String institutionName;
    private String voterID;
    private String voterInstitution;
    private Double score;

    public VoteResult(String voteId, String name, char staffType, String institutionId, String institutionName, String voterID, String voterInstitution, Double score) {
        this.voteId = voteId;
        this.name = name;
        this.staffType = staffType;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.voterID = voterID;
        this.voterInstitution = voterInstitution;
        this.score = score;
    }
}
