package com.medicine.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.springboot.common.AuthAccess;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.entity.User;
import com.medicine.springboot.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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
    public void deleteMedicine(@PathVariable String mno) {
        medicineService.delete(mno);
    }
}
