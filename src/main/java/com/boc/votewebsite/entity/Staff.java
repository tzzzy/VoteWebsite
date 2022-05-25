package com.boc.votewebsite.entity;

import lombok.Data;

@Data
public class Staff {
    private Integer staff_id;
    private Integer institution;
    private String password;
    private char type;
    private String name;
    private String position;
    private String description;

}
