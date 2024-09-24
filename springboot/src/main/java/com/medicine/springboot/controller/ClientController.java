package com.medicine.springboot.controller;

import com.medicine.springboot.service.AgencyService;
import com.medicine.springboot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/getByCno")
    public Object getByMno(Integer cno)
    {
        return clientService.getByCno(cno);
    }


}