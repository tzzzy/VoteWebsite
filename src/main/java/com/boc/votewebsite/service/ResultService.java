package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.*;
import com.boc.votewebsite.mapper.ResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResultService {
    @Autowired
    private ResultMapper resultMapper;

    public List<VoteResult> findResultById(Integer projectId){
        return resultMapper.findResultById(projectId);
    }

    public Integer addResult(Integer projectId, List<VoteResult> results){
        Integer re = 0;
        if(results.size()==0){
            return 0;
        }
        for(int i = 0; i< results.size(); i++){
            String staffId = results.get(i).getStaffId();
            double equal = 0;
            double supsub = 0;
            double average = 0;
            if(results.get(i).getEqual()!=null){
                equal = results.get(i).getEqual();
            }
            if(results.get(i).getSupSub() != null){
                supsub = results.get(i).getSupSub();
            }
            if(results.get(i).getAverage() != null){
                average = results.get(i).getAverage();
            }
            re += resultMapper.addResult(projectId,staffId,equal, supsub, average);
        }
        return re;
    }

    public List<VoteResult> findResultByYear(Integer year){
        return resultMapper.findResultByYear(year);
    }

    public List<VoteResult> findResultByYearAndType(Integer year, char type){
        return resultMapper.findResultByYearAndType(year, type);
    }

    public List<VoteResult> findResultByIdAndType(Integer projectId, char type){
        return resultMapper.findResultByIdAndType(projectId, type);
    }
}
