package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

//员工信息管理时传输的对象
@Data
public class StaffManage implements Serializable {
    private Integer staff_id;
    private String institution_name;

    private Integer institution_id;
    private char type;
    private String staff_name;
    private String position;

    public StaffManage(Integer staff_id, String institution_name, Integer institution_id, char type, String staff_name, String position) {
        this.staff_id = staff_id;
        this.institution_name = institution_name;
        this.institution_id = institution_id;
        this.type = type;
        this.staff_name = staff_name;
        this.position = position;
    }
}
