package com.boc.votewebsite.service;

import com.boc.votewebsite.entity.InstitutionCType;
import com.boc.votewebsite.entity.Staff;
import com.boc.votewebsite.entity.StaffExport;
import com.boc.votewebsite.entity.StaffManage;
import com.boc.votewebsite.mapper.StaffMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;
    public List<Staff> matchStaffIdPassword(String staffId, String password){
        return staffMapper.findByStaffIdPassword(staffId, password);
    }

    public List<StaffManage> findAllStaff(){
        return staffMapper.findAllExceptC();
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

    public Integer updatePasswordByStaffId(String id, String password) {
        return staffMapper.updatePasswordByStaffId(id,password);
    }

    public Map<String,Integer> findCAmount(){
        List<InstitutionCType> ins = staffMapper.findCAmount();
        Map<String,Integer> insCPair = new HashMap<String,Integer>();
        for(int i = 0; i < ins.size(); i++){
            insCPair.put(ins.get(i).getInstitution(),ins.get(i).getAmount());
        }
        return insCPair;
    }

    public Integer addCType(String institution, Integer amountNew, Integer amountOld){
        //amountNew 大于 amountOld，故新增
        Integer re = 0;
        for(Integer i = amountOld; i< amountNew; i++){
            String name = "职员" + i;
            String staffId ="C" + institution + i;
            String position = "投票职员" + i;
            re += staffMapper.addStaff(staffId,institution,'C',name,position);
        }
        return re;
    }

    public Integer deleteCType(String institution, Integer amountNew, Integer amountOld){
        //amountNew 小于 amountOld，故删除
        Integer re = 0;
        for(Integer i = amountNew; i<amountOld; i++){
            String staffId ="C" + institution + i;
            re += staffMapper.deleteById(staffId);
        }
        return re;
    }
}

