package com.medicine.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.springboot.common.AuthAccess;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @RequestMapping("/getByMno")
    public Object getByMno(Integer mno)
    {
        return medicineService.getByMno(mno);
    }

//    @GetMapping("/find")
//    public List<Medicine> getAllMedicines() {
//        return medicineService.findAll();
//    }

    @GetMapping("/find")
    public Result getAllUser() throws SQLException {
        List<Medicine> medicines = medicineService.list(new QueryWrapper<Medicine>().orderByDesc("mno"));
        return Result.success(medicines);
    }

    @PostMapping("/save")
    public boolean addMedicine(@RequestBody Medicine medicine) {
        return medicineService.save(medicine);
    }

    @PutMapping("/update/{mno}")
    public Medicine updateMedicine(@PathVariable Integer mno, @RequestBody Medicine medicine) {
        medicine.setMno(mno);
        return medicineService.update(medicine);
    }

    @DeleteMapping("/delete/{mno}")
    public void deleteMedicine(@PathVariable Integer mno) {
        medicineService.delete(mno);
    }

    @DeleteMapping("/deleteBatch")
    public ResponseEntity<?> deleteBatch(@RequestBody List<Integer> mnos) {
        // 批量删除逻辑
        for (Integer mno : mnos) {
            medicineService.delete(mno);// 执行单个删除操作
        }
        // 创建响应的 JSON 对象
        Map<String, String> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "批量删除成功");

        return ResponseEntity.ok(response); // 返回标准的 JSON 响应
    }

    @PutMapping("/reorder")
    public Result reorderMedicines(@RequestBody List<Medicine> medicines) throws SQLException {
        // 遍历药品列表并更新药品编号
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            // 设置新的药品编号，假设从 1 开始
            medicine.setMno(i + 1);
            // 更新药品信息
            medicineService.updateById(medicine);
        }
        return Result.success("药品编号重新排序成功");
    }
}
