package com.boc.votewebsite.mapper;


import com.boc.votewebsite.entity.InstitutionCType;
import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.entity.StaffManage;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface StaffMapper {
    @Select("SELECT * FROM STAFF WHERE STAFF_ID = #{staffId} and PASSWORD = #{password}" )
    List<Staff> findByStaffIdPassword(String staffId, String password);

    @Select("SELECT STAFF_ID, INSTITUTION.INSTITUTION_NAME, INSTITUTION.INSTITUTION_ID, TYPE,STAFF.STAFF_NAME,POSITION FROM STAFF ,INSTITUTION WHERE STAFF_ID <> '123456789' AND STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID")
    List<StaffManage> findAll();

    @Select("SELECT STAFF_ID, INSTITUTION.+INSTITUTION_NAME, INSTITUTION.INSTITUTION_ID, TYPE,STAFF.STAFF_NAME,POSITION FROM STAFF ,INSTITUTION WHERE STAFF_ID <> '123456789' AND STAFF.INSTITUTION = INSTITUTION.INSTITUTION_ID AND STAFF_NAME = #{staffName}")
    List<StaffManage> findByName(String staffName);

    @Update("UPDATE STAFF SET INSTITUTION = #{institution},TYPE = #{type}, STAFF_NAME = #{staffName}, POSITION = #{position} WHERE STAFF_ID = #{staffId}")
    Integer updateById(String staffId, String institution, char type, String staffName, String position);

    @Select("SELECT STAFF_ID, STAFF_NAME, INSTITUTION, PASSWORD FROM STAFF WHERE STAFF_ID <> '123456789'")
    List<StaffExport> findAllExport();

    @Delete("DELETE FROM STAFF WHERE STAFF_ID =#{id}")
    Integer deleteById(String id);

    @Insert("INSERT INTO STAFF VALUES (#{id}, #{institution}, null, #{type}, #{name}, #{position})")
    Integer addStaff(String id, String institution, char type, String name, String position);

    @Select("SELECT * FROM STAFF WHERE STAFF_ID = #{id}")
    List<Staff> findById(String id);

    @Update("UPDATE STAFF SET PASSWORD = 456789 WHERE STAFF_ID = 1259138")
    Integer updatePasswordByStaffId(String id, String password);

    @Select("SELECT INSTITUTION, COUNT(STAFF_ID) AS AMOUNT FROM STAFF WHERE TYPE = 'C' GROUP BY INSTITUTION")
    List<InstitutionCType> findCAmount();

}
