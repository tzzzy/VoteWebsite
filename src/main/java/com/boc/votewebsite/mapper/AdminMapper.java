package com.boc.votewebsite.mapper;

import com.boc.votewebsite.entity.Admin;
import com.boc.votewebsite.entity.NamePassword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select ADMIN_ID from ADMIN where ADMIN_NAME = #{name} and PASSWORD = #{password}" )
    Integer findByUsernamePassword(String name, String password);
}
