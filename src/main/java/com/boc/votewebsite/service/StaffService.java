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
    public List<Staff> matchStaffIdPassword(Integer staffId, String password){
        return staffMapper.findByStaffIdPassword(staffId, password);
    }

    public List<StaffManage> findAllStaff(){
        return staffMapper.findAll();
    }

    public List<StaffManage> findByStaffName(String staffName){
        return  staffMapper.findByName(staffName);
    }

    public Integer updateById(Integer staffId, Integer institution, char type, String staffName){
        return staffMapper.updateById(staffId,institution,type,staffName);
    }

    public List<StaffExport> findAllExport(){
        return staffMapper.findAllExport();
    }
}
