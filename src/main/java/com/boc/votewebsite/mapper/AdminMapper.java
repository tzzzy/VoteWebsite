package com.boc.votewebsite.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper {
    @Select("select ADMIN_ID from ADMIN where ADMIN_NAME = #{name} and PASSWORD = #{password}" )
    Integer findByUsernamePassword(String name, String password);
}
