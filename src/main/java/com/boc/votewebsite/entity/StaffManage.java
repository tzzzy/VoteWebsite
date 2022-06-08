package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaffManage implements Serializable {
    private Integer staff_id;
    private String institution_name;
    private char type;
    private String staff_name;
    private String position;

    public StaffManage(Integer staff_id, String institution_name, char type, String staff_name, String position) {
        this.staff_id = staff_id;
        this.institution_name = institution_name;
        this.type = type;
        this.staff_name = staff_name;
        this.position = position;
    }
}
