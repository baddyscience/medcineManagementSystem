package com.medicine.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.springboot.entity.Medicine;
import org.apache.ibatis.annotations.*;

public interface MedicineMapper extends BaseMapper<Medicine> {

    @Select("select * from `medicine` where mno = #{mno}")
    Medicine getByMno(Integer mno);
    @Select("select * from `medicine` where mname like concat('%',#{mname},'%')")
    Medicine getByMname(String mname);

}
