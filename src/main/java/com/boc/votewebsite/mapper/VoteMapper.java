package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Vote;
import com.boc.votewebsite.entity.VoteList;
import com.boc.votewebsite.entity.VoteProgress;
import com.boc.votewebsite.entity.VoteResult;
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

    @Select("SELECT VOTE.VOTE_ID, STAFF.STAFF_NAME, STAFF.TYPE STAFFTYPE ,STAFF.INSTITUTION STAFFINSTITUTION, INSTITUTION.INSTITUTION_NAME STAFFINSTITUTIONNAME, VOTER.INSTITUTION VOTERINSTITUTION,VOTE.TYPE VOTETYPE, SUM(VOTE.SCORE) TOTAL\n" +
            "FROM VOTE\n" +
            "    INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "    INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "    INNER JOIN INSTITUTION on STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID\n" +
            "    INNER JOIN INSTITUTION VI on VOTER.INSTITUTION = VI.INSTITUTION_ID\n" +
            "WHERE PROJECT_ID=#{projectId} AND STAFF.TYPE=#{type}\n" +
            "GROUP BY (VOTE.VOTE_ID, STAFF.STAFF_NAME, STAFF.TYPE,STAFF.INSTITUTION, INSTITUTION.INSTITUTION_NAME, VOTER.INSTITUTION, VOTE.TYPE)")
    List<VoteResult> findByProjectAndStaffType(Integer projectId, char type);

    @Select("SELECT INSTITUTION, COUNTTABLE.INSNAME INSNAME,COUNT(COUNTTABLE.VOTER_ID) ALLACCOUNT, COUNT(CASE WHEN COUNTTABLE.USED= COUNTTABLE.TOTAL THEN 1 ELSE NULL END) USEDACCOUNT\n" +
            "FROM\n" +
            "(SELECT INSTITUTION, INSTITUTION.INSTITUTION_NAME INSNAME,VOTER_ID, COUNT(SCORE) AS USED,COUNT(VOTE_ID) AS TOTAL\n" +
            "FROM VOTE\n" +
            "         INNER JOIN STAFF ON VOTE.VOTER_ID = STAFF.STAFF_ID\n" +
            "         INNER JOIN INSTITUTION ON INSTITUTION.INSTITUTION_ID = STAFF.INSTITUTION\n" +
            "WHERE PROJECT_ID = #{projectId}\n" +
            "GROUP BY (INSTITUTION, INSTITUTION.INSTITUTION_NAME,VOTER_ID)) COUNTTABLE\n" +
            "GROUP BY (INSTITUTION, COUNTTABLE.INSNAME)")
    List<VoteProgress> getProgressById(Integer projectId);

    @Select("SELECT VOTE.VOTE_ID, STAFF.STAFF_NAME, STAFF.TYPE STAFFTYPE ,STAFF.INSTITUTION STAFFINSTITUTION, INSTITUTION.INSTITUTION_NAME STAFFINSTITUTIONNAME, VOTER.INSTITUTION VOTERINSTITUTION,VOTE.TYPE VOTETYPE, SUM(VOTE.SCORE) TOTAL\n" +
            "FROM VOTE\n" +
            "INNER JOIN PROJECT ON VOTE.PROJECT_ID = PROJECT.PROJECT_ID\n" +
            "INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "INNER JOIN INSTITUTION on STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID\n" +
            "INNER JOIN INSTITUTION VI on VOTER.INSTITUTION = VI.INSTITUTION_ID\n" +
            "WHERE STAFF.TYPE='A' AND PROJECT.YEAR = 2022\n" +
            "GROUP BY (VOTE.VOTE_ID, STAFF.STAFF_NAME, STAFF.TYPE,STAFF.INSTITUTION, INSTITUTION.INSTITUTION_NAME, VOTER.INSTITUTION, VOTE.TYPE)\n")
    List<VoteResult> findByStaffTypeAndYear(Integer year, char type);

}
