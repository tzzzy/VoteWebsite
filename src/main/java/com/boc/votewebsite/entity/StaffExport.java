package com.boc.votewebsite.entity;

import lombok.Data;

//项目开始前员工账号密码等信息导出时的对象
@Data
public class StaffExport {
    private Integer staff_id;
    private String staff_name;
    private Integer institution;
    private String password;

    public StaffExport(Integer staff_id, String staff_name, Integer institution, String password) {
        this.staff_id = staff_id;
        this.staff_name = staff_name;
        this.institution = institution;
        this.password = password;
    }
}
