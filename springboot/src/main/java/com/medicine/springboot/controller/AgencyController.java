package com.medicine.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.Agency;
import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baomidou.dynamic.datasource.annotation.DS;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/agency")
@DS("medicine")
public class AgencyController {

    @Autowired
    AgencyService agencyService;

    @RequestMapping("/getByAno")
    @DS("medicine")
    public Object getByAno(Integer ano)
    {
        return agencyService.getByAno(ano);
    }
    @GetMapping("/find")
    @DS("medicine")
    public Result getAllUser() throws SQLException {
        List<Agency> agencys = agencyService.list(new QueryWrapper<Agency>().orderByDesc("ano"));
        return Result.success(agencys);
    }

    @PostMapping("/save")
    @DS("medicine")
    public boolean addAgency(@RequestBody Agency agency) {
        return agencyService.save(agency);
    }

    @PutMapping("/update/{ano}")
    @DS("medicine")
    public Agency updateagency(@PathVariable Integer ano, @RequestBody Agency agency) {
        agency.setAno(ano);
        return agencyService.update(agency);
    }

    @DeleteMapping("/delete/{ano}")
    @DS("medicine")
    public void deleteagency(@PathVariable Integer ano) {
        agencyService.delete(ano);
    }

    @DeleteMapping("/deleteBatch")
    @DS("medicine")
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
    @DS("medicine")
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

    @GetMapping("/search")
    @DS("medicine")
    public Result searchMedicines(
            @RequestParam(required = false) String ano,
            @RequestParam(required = false) String aname,
            @RequestParam(required = false) String asex,
            @RequestParam(required = false) String aphone,
            @RequestParam(required = false) String aremark) throws SQLException {

        QueryWrapper<Agency> queryWrapper = new QueryWrapper<>();

        // 根据传入的参数添加查询条件
        if (ano != null && !ano.isEmpty()) {
            queryWrapper.eq("ano", ano);
        }
        if (aname != null && !aname.isEmpty()) {
            queryWrapper.like("aname", aname); // 使用like匹配名称
        }
        if (asex != null && !asex.isEmpty()) {
            queryWrapper.eq("asex", asex);
        }
        if (aphone != null && !aphone.isEmpty()) {
            queryWrapper.like("aphone", aphone); // 使用like匹配功效
        }
        if (aremark != null && !aremark.isEmpty()) {
            queryWrapper.like("aremark", aremark); // 使用like匹配功效
        }

        // 根据构建的条件查询药品
        List<Agency> agencys = agencyService.list(queryWrapper);

        // 返回结果
        return Result.success(agencys);
    }
}
