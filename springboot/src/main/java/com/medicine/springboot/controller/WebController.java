package com.medicine.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.springboot.common.AuthAccess;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.exception.serviceException;
import com.medicine.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * Administrator
 * 2023/8/13 10:35
 */
//@CrossOrigin
@RestController
@RequestMapping()
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result index() {
        return Result.success("sucess");
    }


    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            throw new serviceException("用户名或密码不能为空");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())){
            throw new serviceException("用户名或密码不能为空");
        }
        user = userService.register(user);
        return Result.success(user);
    }



}