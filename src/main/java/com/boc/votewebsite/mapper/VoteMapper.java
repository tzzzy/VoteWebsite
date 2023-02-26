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
            "WHERE STAFF.TYPE=#{type} AND PROJECT.YEAR = #{year}\n" +
            "GROUP BY (VOTE.VOTE_ID, STAFF.STAFF_NAME, STAFF.TYPE,STAFF.INSTITUTION, INSTITUTION.INSTITUTION_NAME, VOTER.INSTITUTION, VOTE.TYPE)\n")
    List<VoteResult> findByStaffTypeAndYear(Integer year, char type);

    @Select("SELECT EQUALSCORE.VOTE_ID AS STAFFID, STAFF.STAFF_NAME, STAFF.TYPE,STAFF.INSTITUTION,INSTITUTION.INSTITUTION_NAME, EQUAL, SUPSUB, AVERAGE\n" +
            "FROM\n" +
            "(SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS EQUAL\n" +
            "FROM VOTE\n" +
            "INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "WHERE STAFF.TYPE = 'A' AND PROJECT_ID = #{projectId} AND VOTE.TYPE = 'equal'\n" +
            "GROUP BY VOTE.VOTE_ID) EQUALSCORE\n" +
            "INNER JOIN (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS SUPSUB\n" +
            "            FROM VOTE\n" +
            "                     INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "            WHERE STAFF.TYPE = 'A' AND AND VOTE.SCORE IS NOT NULL PROJECT_ID = #{projectId} AND (VOTE.TYPE = 'superior' OR VOTE.TYPE = 'subordinate')\n" +
            "            GROUP BY VOTE.VOTE_ID) SUPSUBSCORE ON EQUALSCORE.VOTE_ID = SUPSUBSCORE.VOTE_ID\n" +
            "INNER JOIN (SELECT ASCORE.VOTE_ID, ASCORE.A*0.3 + BSCORE.B*0.2 + CSCORE.C*0.5 AS AVERAGE\n" +
            "            FROM (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS A\n" +
            "                  FROM VOTE\n" +
            "                           INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                           INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                  WHERE STAFF.TYPE = 'A' AND VOTE.SCORE IS NOT NULL AND  PROJECT_ID = #{projectId} AND VOTER.TYPE = 'A'\n" +
            "                  GROUP BY VOTE.VOTE_ID) ASCORE\n" +
            "                     INNER JOIN (\n" +
            "                SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS B\n" +
            "                FROM VOTE\n" +
            "                         INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                         INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                WHERE STAFF.TYPE = 'A' AND PROJECT_ID = #{projectId} AND VOTER.TYPE = 'B'\n" +
            "                GROUP BY VOTE.VOTE_ID\n" +
            "            ) BSCORE ON ASCORE.VOTE_ID = BSCORE.VOTE_ID\n" +
            "                     INNER JOIN (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS C\n" +
            "                                 FROM VOTE\n" +
            "                                          INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                                          INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                                 WHERE STAFF.TYPE = 'A' AND VOTE.SCORE IS NOT NULL AND PROJECT_ID = #{projectId} AND VOTER.TYPE = 'C'\n" +
            "                                 GROUP BY VOTE.VOTE_ID\n" +
            "            ) CSCORE ON ASCORE.VOTE_ID = CSCORE.VOTE_ID) AVESCORE ON EQUALSCORE.VOTE_ID = AVESCORE.VOTE_ID\n" +
            "INNER JOIN STAFF ON EQUALSCORE.VOTE_ID = STAFF.STAFF_ID\n" +
            "INNER JOIN INSTITUTION ON STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID")
    List<VoteResult> findAResultByProjectId(Integer projectId);

    @Select("SELECT EQUALSCORE.VOTE_ID AS STAFFID, STAFF.STAFF_NAME, STAFF.TYPE,STAFF.INSTITUTION,INSTITUTION.INSTITUTION_NAME, EQUAL, SUPSUB, AVERAGE\n" +
            "FROM\n" +
            "(SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS EQUAL\n" +
            "FROM VOTE\n" +
            "INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "WHERE STAFF.TYPE = 'B' AND PROJECT_ID = #{projectId} AND VOTE.TYPE = 'equal'\n" +
            "GROUP BY VOTE.VOTE_ID) EQUALSCORE\n" +
            "INNER JOIN (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS SUPSUB\n" +
            "            FROM VOTE\n" +
            "                     INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "            WHERE STAFF.TYPE = 'B' AND VOTE.SCORE IS NOT NULL AND PROJECT_ID = #{projectId} AND (VOTE.TYPE = 'superior' OR VOTE.TYPE = 'subordinate')\n" +
            "            GROUP BY VOTE.VOTE_ID) SUPSUBSCORE ON EQUALSCORE.VOTE_ID = SUPSUBSCORE.VOTE_ID\n" +
            "INNER JOIN (SELECT ASCORE.VOTE_ID, ASCORE.A*0.2 + BSCORE.B*0.3 + CSCORE.C*0.5 AS AVERAGE\n" +
            "            FROM (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS A\n" +
            "                  FROM VOTE\n" +
            "                           INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                           INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                  WHERE STAFF.TYPE = 'B' AND VOTE.SCORE IS NOT NULL AND PROJECT_ID = #{projectId} AND VOTER.TYPE = 'A'\n" +
            "                  GROUP BY VOTE.VOTE_ID) ASCORE\n" +
            "                     INNER JOIN (\n" +
            "                SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS B\n" +
            "                FROM VOTE\n" +
            "                         INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                         INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                WHERE STAFF.TYPE = 'B' AND PROJECT_ID = #{projectId} AND VOTER.TYPE = 'B'\n" +
            "                GROUP BY VOTE.VOTE_ID\n" +
            "            ) BSCORE ON ASCORE.VOTE_ID = BSCORE.VOTE_ID\n" +
            "                     INNER JOIN (SELECT VOTE_ID, SUM(VOTE.SCORE)/COUNT(VOTE.VOTER_ID) AS C\n" +
            "                                 FROM VOTE\n" +
            "                                          INNER JOIN STAFF ON VOTE.VOTE_ID = STAFF.STAFF_ID\n" +
            "                                          INNER JOIN STAFF VOTER ON VOTE.VOTER_ID = VOTER.STAFF_ID\n" +
            "                                 WHERE STAFF.TYPE = 'B' AND VOTE.SCORE IS NOT NULL AND PROJECT_ID = #{projectId} AND VOTER.TYPE = 'C'\n" +
            "                                 GROUP BY VOTE.VOTE_ID\n" +
            "            ) CSCORE ON ASCORE.VOTE_ID = CSCORE.VOTE_ID) AVESCORE ON EQUALSCORE.VOTE_ID = AVESCORE.VOTE_ID\n" +
            "INNER JOIN STAFF ON EQUALSCORE.VOTE_ID = STAFF.STAFF_ID\n" +
            "INNER JOIN INSTITUTION ON STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID")
    List<VoteResult> findBResultByProjectId(Integer projectId);

}
