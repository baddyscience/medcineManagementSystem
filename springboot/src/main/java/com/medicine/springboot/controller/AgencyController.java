package com.medicine.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    AgencyService agencyService;

    @RequestMapping("/getByAno")
    public Object getByAno(Integer ano)
    {
        return agencyService.getByAno(ano);
    }
    @GetMapping("/find")
    public Result getAllUser() throws SQLException {
        List<Agency> agencys = agencyService.list(new QueryWrapper<Agency>().orderByDesc("ano"));
        return Result.success(agencys);
    }

    @PostMapping("/save")
    public boolean addAgency(@RequestBody Agency agency) {
        return agencyService.save(agency);
    }

    @PutMapping("/update/{ano}")
    public Agency updateagency(@PathVariable Integer ano, @RequestBody Agency agency) {
        agency.setAno(ano);
        return agencyService.update(agency);
    }

    @DeleteMapping("/delete/{ano}")
    public void deleteagency(@PathVariable Integer ano) {
        agencyService.delete(ano);
    }

    @DeleteMapping("/deleteBatch")
    public ResponseEntity<?> deleteBatch(@RequestBody List<Integer> anos) {
        // 批量删除逻辑
        for (Integer ano : anos) {
            agencyService.delete(ano);// 执行单个删除操作
        }
        // 创建响应的 JSON 对象
        Map<String, String> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "批量删除成功");

        return ResponseEntity.ok(response); // 返回标准的 JSON 响应
    }

    @PutMapping("/reorder")
    public Result reorderagencys(@RequestBody List<Agency> agencys) throws SQLException {
        // 遍历药品列表并更新药品编号
        for (int i = 0; i < agencys.size(); i++) {
            Agency agency = agencys.get(i);
            // 设置新的药品编号，假设从 1 开始
            agency.setAno(i + 1);
            // 更新药品信息
            agencyService.updateById(agency);
        }
        return Result.success("经办人编号重新排序成功");
    }
}
