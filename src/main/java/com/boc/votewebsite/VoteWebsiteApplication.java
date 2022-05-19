package com.boc.votewebsite;

import com.boc.votewebsite.entity.Admin;
import com.boc.votewebsite.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
public class VoteWebsiteApplication {


    public static void main(String[] args) {
        SpringApplication.run(VoteWebsiteApplication.class, args);
    }


}
