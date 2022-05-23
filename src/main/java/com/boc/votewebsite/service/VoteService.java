package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteMapper voteMapper;

    public List<Vote> getVoteList(Integer projectId, Integer voterId){
        return  voteMapper.findByVoterIdAndProjectId(voterId, projectId);
    }

    public Integer vote(Integer score, Integer projectId, Integer voteId, Integer voterId){
        return voteMapper.voteByProjectIdAndVoteIdAndVoterId(score, projectId, voteId, voterId);
    }
}
