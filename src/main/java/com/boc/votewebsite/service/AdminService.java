package com.boc.votewebsite.service;

import com.boc.votewebsite.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    public Integer matchUsernamePassword(String username, String password){
        Integer re = adminMapper.findByUsernamePassword(username, password);
        if( re ==null){
            return null;
        }
        else {
            return re;
        }
    }
}
