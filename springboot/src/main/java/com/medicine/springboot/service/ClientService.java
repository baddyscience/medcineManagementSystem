package com.medicine.springboot.service;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Client;
import com.medicine.springboot.entity.Medicine;
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


    public Client update(Client client) {
        return clientMapper.updateById(client) > 0 ? client : null;
    }

    public void delete(Integer cno) {
        clientMapper.deleteById(cno);
    }

    public List<Client> searchClients(String cno, String cname, String csex, String cage, String caddress, String cphone, String csymptom, Integer mno, Integer ano, String cdate, String cremark) {
        return java.util.Collections.emptyList();
    }
}