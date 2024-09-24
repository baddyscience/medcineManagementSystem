package com.medicine.springboot.controller;


import com.medicine.springboot.service.AgencyService;
import com.medicine.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
