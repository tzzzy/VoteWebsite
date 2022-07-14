package com.boc.votewebsite.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoteResult implements Serializable {
    private  String staffId;
    private String staffName;
    private char staffType;
    private String staffInstitution;
    private String staffInstitutionName;
    private Double equal;
    private Double supSub;
    private Double average;

    public VoteResult(String staffId, String staffName, char staffType, String staffInstitution, String staffInstitutionName, Double equal, Double supSub, Double average) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffType = staffType;
        this.staffInstitution = staffInstitution;
        this.staffInstitutionName = staffInstitutionName;
        this.equal = equal;
        this.supSub = supSub;
        this.average = average;
    }
}
