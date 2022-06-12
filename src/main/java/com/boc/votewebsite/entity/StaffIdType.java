package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaffIdType implements Serializable {
    private String staffId;
    private char type;

    public StaffIdType(String staffId, char type) {
        this.staffId = staffId;
        this.type = type;
    }
}
