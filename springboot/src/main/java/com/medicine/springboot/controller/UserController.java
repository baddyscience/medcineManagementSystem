package com.medicine.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.springboot.common.AuthAccess;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) throws SQLException {


        User byUsername = userService.getByUsername(user.getUsername());

        if (byUsername != null) {
            return Result.error("用户已存在");
//            return Result.success();
        }

        userService.save(user);
        return Result.success();
    }

    @PutMapping("/updateUserById")
    public Result updateUserById(@RequestBody User user) throws SQLException {
        userService.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/deleteUserById/{id}")
    public Result deleteUserById(@PathVariable int id) throws SQLException {
        userService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteUserBatch")
    public Result deleteUserBatch(@RequestBody List<Integer> ids) throws SQLException {
        System.out.println(ids);
        userService.removeBatchByIds(ids);
        return Result.success();
    }


    @AuthAccess
    @GetMapping("/getAllUser")
    public Result getAllUser() throws SQLException {
        List<User> users = userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(users);
    }

    @AuthAccess
    @GetMapping("/getUserById/{id}")
    public Result getUserById(@PathVariable int id) throws SQLException {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/getUserByPages")
    public Result getUserByPages(@RequestParam int pageNum,
                                 @RequestParam int pageSize,
                                 @RequestParam String username,
                                 @RequestParam String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StrUtil.isNotBlank(username),"username", username);
        queryWrapper.like(StrUtil.isNotBlank(name),"name", name);
        queryWrapper.orderByDesc("id");

        IPage<User> user = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        System.out.println(user);

        return Result.success(user);
    }


}
