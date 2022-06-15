package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.entity.VoteList;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface VoteMapper {
    @Select("SELECT PROJECT_ID, VOTE_ID, STAFF.STAFF_NAME,VOTE.TYPE, VOTER_ID, SCORE FROM VOTE, STAFF WHERE VOTER_ID =#{voterId} AND PROJECT_ID =#{projectId} AND VOTE.VOTE_ID = STAFF.STAFF_ID AND VOTE.TYPE = #{type}")
    List<VoteList> findByVoterIdAndProjectId(String voterId, Integer projectId, String type);

    @Update("UPDATE VOTE SET SCORE =#{score} WHERE PROJECT_ID = #{projectId} AND VOTE_ID = #{voteId} AND VOTER_ID =#{voterId}")
    Integer voteByProjectIdAndVoteIdAndVoterId(Double score, Integer projectId, String voteId, String voterId);

    @Select("SELECT * FROM VOTE WHERE VOTER_ID =#{voterId} AND PROJECT_ID = #{projectId} AND VOTE_ID = #{voteId}")
    List<Vote> findByProjectIdAndVoteIdAndVoterId(Integer projectId, String voteId, String voterId);

    @Insert("INSERT INTO VOTE VALUES (#{projectId} , #{voteId} , #{type}, #{voterId} , null)")
    Integer addNewVote(Integer projectId, String voteId, String voterId, String type);

    @Delete("DELETE FROM VOTE WHERE PROJECT_ID=#{projectId}")
    Integer deleteVote(Integer projectId);


}
