package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.StaffManage;
import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.entity.VoteList;
import com.boc.votewebsite.entity.VoteResult;
import com.boc.votewebsite.mapper.StaffMapper;
import com.boc.votewebsite.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private StaffMapper staffMapper;


    public List<VoteList> getVoteList(Integer projectId, String voterId, String type){
        return  voteMapper.findByVoterIdAndProjectId(voterId, projectId, type);
    }

    public Integer vote(Double score, Integer projectId, String voteId, String voterId){
        return voteMapper.voteByProjectIdAndVoteIdAndVoterId(score, projectId, voteId, voterId);
    }

    public Double getScore(Integer projectId, String voteId, String voterId){
        List<Vote> votes = voteMapper.findByProjectIdAndVoteIdAndVoterId(projectId,voteId, voterId);
        if(votes.size()==0){
            return -1.0;
        }
        if(votes.get(0).getScore() == null){
            return null;
        }
        return votes.get(0).getScore();
    }

    public Integer deleteVotes(Integer projectId){
        return voteMapper.deleteVote(projectId);
    }

    public Integer addVotes(Integer projectId){
        List<StaffManage> staffs = staffMapper.findAll();
        for(int i = 0; i < staffs.size(); i++){
            for(int j = 0; j < staffs.size(); j++){
                if(i==j){
                    continue;
                }
                String voterId = staffs.get(i).getStaff_id();
                char voterType = staffs.get(i).getType();
                String voterIns = staffs.get(i).getInstitution_id();
                String voteId = staffs.get(j).getStaff_id();
                char voteType = staffs.get(j).getType();
                String voteIns = staffs.get(j).getInstitution_id();
                if(voterType =='A'){
                    if(voteType == 'A'){
                        if(voteMapper.addNewVote(projectId,voteId,voterId, "equal") == 0){
                            return 0;
                        }
                    }
                    if(voteType == 'B' && voterIns.equals(voteIns)){
                        if(voteMapper.addNewVote(projectId,voteId,voterId, "superior") == 0){
                            return 0;
                        }
                    }
                }
                if(voterType =='B' && voterIns.equals(voteIns)){
                    if(voteType == 'A'){
                        if(voteMapper.addNewVote(projectId,voteId,voterId, "subordinate") == 0){
                            return 0;
                        }
                    }
                    if(voteType == 'B'){
                        if(voteMapper.addNewVote(projectId,voteId,voterId, "equal") == 0){
                            return 0;
                        }
                    }
                }
                if(voterType =='C' && voterIns.equals(voteIns)){
                    if(voteType != 'C'){
                        if(voteMapper.addNewVote(projectId,voteId,voterId, "subordinate") == 0){
                            return 0;
                        }
                    }
                }
            }
        }
        return 1;
    }

    public List<VoteResult> findByProjectIdAndStaffType(Integer projectId, char type){
        return voteMapper.findByProjectAndStaffType(projectId, type);
    }

}
