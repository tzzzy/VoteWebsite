package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.*;
import com.boc.votewebsite.mapper.ResultMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResultService {
    @Autowired
    private ResultMapper resultMapper;

    List<VoteResult> findResultById(Integer projectId){
        return resultMapper.findResultById(projectId);
    }

    Integer addResult(Integer projectId, List<VoteResult> results){
        Integer re = 0;
        for(int i = 0; i< results.size(); i++){
            String voteId = results.get(i).getVoteId();
            String staffName = results.get(i).getStaffName();
            char staffType = results.get(i).getStaffType();
            String staffInstitution = results.get(i).getStaffInstitution();
            String staffInstitutionName = results.get(i).getStaffInstitutionName();
            String voterInstitution = results.get(i).getVoterInstitution();
            String voteType = results.get(i).getVoteType();
            Double total = results.get(i).getTotal();
            re += resultMapper.addResult(projectId,voteId,staffName,staffInstitution,staffInstitutionName,voterInstitution,voteType,total);
        }
        return re;
    }

    List<VoteResult> findResultByYear(Integer year){
        return resultMapper.findResultByYear(year);
    }
}
