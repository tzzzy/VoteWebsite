package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.entity.VoteList;
import com.boc.votewebsite.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteMapper voteMapper;

    public List<VoteList> getVoteList(Integer projectId, Integer voterId){
        return  voteMapper.findByVoterIdAndProjectId(voterId, projectId);
    }

    public Integer vote(Integer score, Integer projectId, Integer voteId, Integer voterId){
        return voteMapper.voteByProjectIdAndVoteIdAndVoterId(score, projectId, voteId, voterId);
    }

    public Integer getScore(Integer projectId, Integer voteId, Integer voterId){
        List<Vote> votes = voteMapper.findByProjectIdAndVoteIdAndVoterId(projectId,voteId, voterId);
        if(votes.size()==0){
            return -1;
        }
        if(votes.get(0).getScore() == null){
            return 0;
        }
        return votes.get(0).getScore();
    }
}
