package com.boc.votewebsite.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoteResult implements Serializable {
    private  String voteId;
    private String staffName;
    private char staffType;
    private String staffInstitution;
    private String staffInstitutionName;
    private String voterInstitution;
    private String voteType;
    private Double total;

    public VoteResult(String voteId, String staffName, char staffType, String staffInstitution, String staffInstitutionName, String voterInstitution, String voteType, Double total) {
        this.voteId = voteId;
        this.staffName = staffName;
        this.staffType = staffType;
        this.staffInstitution = staffInstitution;
        this.staffInstitutionName = staffInstitutionName;
        this.voterInstitution = voterInstitution;
        this.voteType = voteType;
        this.total = total;
    }
}
