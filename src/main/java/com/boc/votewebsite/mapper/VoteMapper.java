package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.entity.VoteList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface VoteMapper {
    @Select("SELECT ID, PROJECT_ID, VOTE_ID, STAFF.STAFF_NAME,VOTE.TYPE, VOTER_ID, SCORE FROM VOTE, STAFF WHERE VOTER_ID =#{voterId} AND PROJECT_ID =#{projectId} AND VOTE.VOTE_ID = STAFF.STAFF_ID")
    List<VoteList> findByVoterIdAndProjectId(String voterId, Integer projectId);

    @Update("UPDATE VOTE SET SCORE =#{score} WHERE PROJECT_ID = #{projectId} AND VOTE_ID = #{voteId} AND VOTER_ID =#{voterId}")
    Integer voteByProjectIdAndVoteIdAndVoterId(Integer score, Integer projectId, String voteId, String voterId);

    @Select("SELECT * FROM VOTE WHERE VOTER_ID =#{voterId} AND PROJECT_ID = #{projectId} AND VOTE_ID = #{voteId}")
    List<Vote> findByProjectIdAndVoteIdAndVoterId(Integer projectId, String voteId, String voterId);


}
