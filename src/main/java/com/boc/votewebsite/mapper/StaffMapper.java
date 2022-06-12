package com.boc.votewebsite.mapper;


import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.entity.StaffManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface StaffMapper {
    @Select("SELECT * FROM STAFF WHERE STAFF_ID = #{staffId} and PASSWORD = #{password}" )
    List<Staff> findByStaffIdPassword(String staffId, String password);

    @Select("SELECT STAFF_ID, INSTITUTION.INSTITUTION_NAME, INSTITUTION.INSTITUTION_ID, TYPE,STAFF.STAFF_NAME,POSITION FROM STAFF ,INSTITUTION WHERE STAFF_ID <> '123456789' AND STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID")
    List<StaffManage> findAll();

    @Select("SELECT STAFF_ID, INSTITUTION.INSTITUTION_NAME, INSTITUTION.INSTITUTION_ID, TYPE,STAFF.STAFF_NAME,POSITION FROM STAFF ,INSTITUTION WHERE STAFF_ID <> '123456789' AND STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID AND STAFF_NAME = #{staffName}")
    List<StaffManage> findByName(String staffName);

    @Update("UPDATE STAFF SET INSTITUTION = #{institution},TYPE = #{type}, STAFF_NAME = #{staffName} WHERE STAFF_ID = #{staffId}")
    Integer updateById(Integer staffId, Integer institution, char type, String staffName);

    @Select("SELECT STAFF_ID, STAFF_NAME, INSTITUTION, PASSWORD FROM STAFF WHERE STAFF_ID <> '123456789'")
    List<StaffExport> findAllExport();
}
