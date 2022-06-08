package com.boc.votewebsite.service;


import com.alibaba.fastjson.JSONObject;
import com.boc.votewebsite.entity.Institution;
import com.boc.votewebsite.mapper.InstitutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InstitutionService {
    @Autowired
    private InstitutionMapper institutionMapper;

    public JSONObject findAll(){
        JSONObject result = new JSONObject();
        List<Institution> ins = institutionMapper.findAll();
        for(int i = 0; i < ins.size(); i++){
            result.put(ins.get(i).getInstitution_id().toString(),ins.get(i).getInstitution_name());
        }
        return result;
    }
}
