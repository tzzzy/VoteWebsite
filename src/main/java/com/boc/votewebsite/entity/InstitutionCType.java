package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class InstitutionCType implements Serializable {
    private String institution;
    private Integer amount;

    public InstitutionCType(String institution, Integer amount) {
        this.institution = institution;
        this.amount = amount;
    }
}
