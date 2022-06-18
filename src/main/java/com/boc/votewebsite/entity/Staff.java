package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Staff implements Serializable {
    private String staff_id;
    private String institution;
    private String password;
    private char type;
    private String name;
    private String position;


    public Staff(String staff_id, String institution, String password, char type, String name, String position) {
        this.staff_id = staff_id;
        this.institution = institution;
        this.password = password;
        this.type = type;
        this.name = name;
        this.position = position;

    }
}
