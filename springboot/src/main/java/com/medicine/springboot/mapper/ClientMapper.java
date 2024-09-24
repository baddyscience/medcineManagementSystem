package com.medicine.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.springboot.entity.Client;
import org.apache.ibatis.annotations.*;

public interface ClientMapper extends BaseMapper<Client> {

    @Select("select * from `client` where cno = #{cno}")
    Client getByCno(Integer cno);
}

