package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ProjectMapper {
    @Select("SELECT * FROM PROJECT WHERE START_TIME < #{time} AND END_TIME > #{time}")
    List<Project> findByTime(Timestamp time);

    @Select("SELECT * FROM PROJECT WHERE SEASON =#{season} AND YEAR =#{year}")
    List<Project> findBySeasonAndYear(Integer season, Integer year);

    @Insert("INSERT INTO PROJECT VALUES (PROJECT_SEQ.NEXTVAL, #{startTime}, #{endTime}, #{createTime}, #{season}, #{year})")
    Integer addProject(Timestamp startTime, Timestamp endTime, Timestamp createTime, Integer season, Integer year);

    @Delete("DELETE FROM PROJECT WHERE PROJECT_ID=#{projectId}")
    Integer deleteProject(Integer projectId);

    @Select("SELECT * FROM PROJECT WHERE YEAR =#{year}")
    List<Project> findByYear(Integer year);
}
