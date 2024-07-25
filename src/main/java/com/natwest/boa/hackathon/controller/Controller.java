package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class Controller {

    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/payments")
    public List<String> getAllEmployees() {
        return service.getAllPayments();
    }

}
