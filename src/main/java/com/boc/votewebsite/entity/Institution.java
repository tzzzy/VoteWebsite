package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Institution implements Serializable {
    private String institution_id;
    private String institution_name;

    public Institution(String institution_id, String institution_name) {
        this.institution_id = institution_id;
        this.institution_name = institution_name;
    }
}
