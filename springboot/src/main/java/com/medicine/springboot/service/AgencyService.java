package com.medicine.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Client;
import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.mapper.AgencyMapper;
import com.medicine.springboot.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AgencyService extends ServiceImpl<AgencyMapper, Agency> {

    @Resource
    AgencyMapper agencyMapper;

    private Agency getAgency(Integer ano) {
        QueryWrapper<Agency> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ano", ano);

        return agencyMapper.selectOne(queryWrapper);
    }

    public Agency getByAno(Integer ano) {
        return getAgency(ano);
    }

    public List<Agency> findAll() {
        return agencyMapper.selectList(null);
    }

    public Agency update(Agency agency) {
        return agencyMapper.updateById(agency) > 0 ? agency : null;
    }

    public void delete(Integer ano) {
        agencyMapper.deleteById(ano);
    }
}
