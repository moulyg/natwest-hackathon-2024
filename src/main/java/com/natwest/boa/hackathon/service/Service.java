package com.natwest.boa.hackathon.service;


import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    public List<String> getAllPayments() {

        return List.of("500.00");
    }
}
