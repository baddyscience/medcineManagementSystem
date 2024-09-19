package com.medicine.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.exception.serviceException;
import com.medicine.springboot.mapper.UserMapper;
import com.medicine.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    //    @Autowired
    @Resource
    UserMapper userMapper;

    private User getUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username); // 相当于 where username = #{username}

        return userMapper.selectOne(queryWrapper);  // 相当于 select * from user where username = #{username}
    }

    public User login(User user) {

        User dbUser = getUser(user.getUsername());

        if (dbUser==null){
            throw new serviceException("用户名不存在");
        }
        if (!user.getPassword().equals(dbUser.getPassword())){
            throw new serviceException("密码错误");

        }
        String token = TokenUtils.createToken(String.valueOf(dbUser.getId()), dbUser.getPassword());
        dbUser.setToken(token);

        return dbUser;
    }

    public User register(User user) {

        User dbUser = getUser(user.getUsername());

        if (dbUser!=null){
            throw new serviceException("用户名已存在");
        }
        userMapper.insert(user);
        return user;
    }


    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }
}
