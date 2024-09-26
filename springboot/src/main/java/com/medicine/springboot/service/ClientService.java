package com.medicine.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Client;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.mapper.AgencyMapper;
import com.medicine.springboot.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientService extends ServiceImpl<ClientMapper, Client> {

    @Resource
    ClientMapper clientMapper;

    private Client getClient(Integer cno) {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cno", cno);

        return clientMapper.selectOne(queryWrapper);
    }

    public Client getByCno(Integer cno) {
        return getClient(cno);
    }

    public List<Client> findAll() {
        return clientMapper.selectList(Wrappers.<Client>lambdaQuery());
    }


}