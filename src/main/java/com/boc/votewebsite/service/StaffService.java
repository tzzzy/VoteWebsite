package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.entity.StaffManage;
import com.boc.votewebsite.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;
    public List<Staff> matchStaffIdPassword(String staffId, String password){
        return staffMapper.findByStaffIdPassword(staffId, password);
    }

    public List<StaffManage> findAllStaff(){
        return staffMapper.findAll();
    }

    public List<StaffManage> findByStaffName(String staffName){
        return  staffMapper.findByName(staffName);
    }

    public Integer updateById(String staffId, String institution, char type, String staffName, String position){
        return staffMapper.updateById(staffId,institution,type,staffName, position);
    }

    public List<StaffExport> findAllExport(){
        return staffMapper.findAllExport();
    }

    public Integer deleteById(String id){
        return staffMapper.deleteById(id);
    }

    public Integer addStaff(String id, String institution, char type, String name, String position){
        return staffMapper.addStaff(id,institution,type,name,position);
    }

    public List<Staff> findById(String id){
        return staffMapper.findById(id);
    }
}
