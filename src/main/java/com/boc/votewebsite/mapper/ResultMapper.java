package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.VoteResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResultMapper {
    @Select("SELECT VOTE_ID,STAFF_NAME,STAFF_TYPE,STAFF_INSTITUTION,STAFF_INSTITUTION_NAME,VOTE_TYPE,TOTAL FROM RESULT WHERE PROJECT_ID =#{projectId}")
    List<VoteResult> findResultById(Integer projectId);

    @Select("")
    List<VoteResult> findResultByYear(Integer year);

    @Insert("INSERT INTO RESULT VALUES (#{projectId}, #{voteId}, #{staffName}, #{staffInstitution}, #{staffInstitutionName}, #{voterInstitution}, #{voteType}, #{total})")
    Integer addResult(Integer projectId, String voteId, String staffName, String staffInstitution, String staffInstitutionName, String voterInstitution, String voteType, Double total);


}
