package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Institution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InstitutionMapper {
    @Select("SELECT * FROM INSTITUTION")
    List<Institution> findAll();
}
