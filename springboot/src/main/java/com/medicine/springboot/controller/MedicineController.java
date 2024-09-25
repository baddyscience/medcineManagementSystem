package com.medicine.springboot.controller;

import com.medicine.springboot.entity.Medicine;
import com.medicine.springboot.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find")
    public List<Medicine> getAllMedicines() {
        return medicineService.findAll();
    }

    @PostMapping("/save")
    public boolean addMedicine(@RequestBody Medicine medicine) {
        return medicineService.save(medicine);
    }

    @PutMapping("/update/{mno}")
    public Medicine updateMedicine(@PathVariable String mno, @RequestBody Medicine medicine) {
        medicine.setMno(mno);
        return medicineService.update(medicine);
    }

    @DeleteMapping("/delete/{mno}")
    public void deleteMedicine(@PathVariable String mno) {
        medicineService.delete(mno);
    }
}
