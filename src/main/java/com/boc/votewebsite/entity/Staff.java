package com.boc.votewebsite.entity;

import lombok.Data;

@Data
public class Staff {
    private Integer staffId;
    private Integer institution;
    private Integer password;
    private char type;
    private String name;
    private String position;
    private String description;

}
