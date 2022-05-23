package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Vote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface VoteMapper {
    @Select("SELECT * FROM VOTE WHERE VOTER_ID =#{voterId} and PROJECT_ID = #{projectId}")
    List<Vote> findByVoterIdAndProjectId(Integer voterId, Integer projectId);

    @Update("UPDATE VOTE SET SCORE =#{score} WHERE PROJECT_ID = #{projectId} AND VOTE_ID = #{voteId} AND VOTER_ID =#{voterId}")
    Integer voteByProjectIdAndVoteIdAndVoterId(Integer score, Integer projectId, Integer voteId, Integer voterId);
}
