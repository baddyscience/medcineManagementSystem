package com.medicine.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Client;
import com.medicine.springboot.mapper.AgencyMapper;
import com.medicine.springboot.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
