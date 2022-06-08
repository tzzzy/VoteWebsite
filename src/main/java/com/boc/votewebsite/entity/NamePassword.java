package com.boc.votewebsite.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class NamePassword implements Serializable {
    String name;
    String password;
}
