package com.medicine.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from `user` where username = #{username}")
    User getByUsername(String username);
}
