package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

//项目开始前员工账号密码等信息导出时的对象
@Data
public class StaffExport implements Serializable {
    private String staff_id;
    private String staff_name;
    private String institution;
    private String password;

    public StaffExport(String staff_id, String staff_name, String institution, String password) {
        this.staff_id = staff_id;
        this.staff_name = staff_name;
        this.institution = institution;
        this.password = password;
    }
}
