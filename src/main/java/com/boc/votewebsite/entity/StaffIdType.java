package com.boc.votewebsite.entity;

import lombok.Data;

@Data
public class StaffIdType {
    private Integer staffId;
    private char type;

    public StaffIdType(Integer staffId, char type) {
        this.staffId = staffId;
        this.type = type;
    }
}
