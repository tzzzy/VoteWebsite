package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ProjectMapper {
    @Select("SELECT * FROM PROJECT WHERE START_TIME < #{time} AND END_TIME > #{time}")
    List<Project> findByTime(Timestamp time);
}
