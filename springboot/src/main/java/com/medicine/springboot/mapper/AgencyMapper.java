package com.medicine.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.springboot.entity.Agency;
import org.apache.ibatis.annotations.*;

public interface AgencyMapper extends BaseMapper<Agency> {

    @Select("select * from `agency` where ano = #{ano}")
    Agency getByAno(Integer ano);
}


