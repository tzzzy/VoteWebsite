package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteProgress implements Serializable {
    private String institution;
    private String insName;
    private Integer allAccount;
    private Integer usedAccount;

    public VoteProgress(String institution, String insName, Integer allAccount, Integer usedAccount) {
        this.institution = institution;
        this.insName = insName;
        this.allAccount = allAccount;
        this.usedAccount = usedAccount;
    }
}
