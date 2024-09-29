package com.medicine.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.mapper.AgencyMapper;
import com.medicine.springboot.mapper.MedicineMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedicineService extends ServiceImpl<MedicineMapper, Medicine> {

    @Resource
    MedicineMapper medicineMapper;

    private Medicine getMedicine(Integer mno) {
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mno", mno);

        return medicineMapper.selectOne(queryWrapper);
    }

    public Medicine getByMno(Integer mno) {
        return getMedicine(mno);
    }

    public List<Medicine> findAll() {
        return medicineMapper.selectList(null);
    }

    public Medicine update(Medicine medicine) {
        return medicineMapper.updateById(medicine) > 0 ? medicine : null;
    }

    public void delete(Integer mno) {
        medicineMapper.deleteById(mno);
    }


}
