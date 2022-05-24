package com.boc.votewebsite.mapper;


import com.boc.votewebsite.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface StaffMapper {
    @Select("select * from STAFF where STAFF_ID = #{staffId} and PASSWORD = #{password}" )
    List<Staff> findByStaffIdPassword(Integer staffId, String password);
}
