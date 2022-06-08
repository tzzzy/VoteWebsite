package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Staff implements Serializable {
    private Integer staff_id;
    private Integer institution;
    private String password;
    private char type;
    private String name;
    private String position;
    private String description;

    public Staff(Integer staff_id, Integer institution, String password, char type, String name, String position, String description) {
        this.staff_id = staff_id;
        this.institution = institution;
        this.password = password;
        this.type = type;
        this.name = name;
        this.position = position;
        this.description = description;
    }
}
