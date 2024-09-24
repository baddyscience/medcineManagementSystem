package com.medicine.springboot.controller;

import com.medicine.springboot.service.ClientService;
import com.medicine.springboot.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
