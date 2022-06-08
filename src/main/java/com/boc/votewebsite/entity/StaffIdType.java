package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaffIdType implements Serializable {
    private Integer staffId;
    private char type;

    public StaffIdType(Integer staffId, char type) {
        this.staffId = staffId;
        this.type = type;
    }
}
