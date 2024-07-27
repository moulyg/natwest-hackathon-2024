package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.service.CashbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class CashBackController {

    private final CashbackService cashbackService;

    public CashBackController(CashbackService cashbackService) {
        this.cashbackService = cashbackService;
    }

    @GetMapping("/cashback/accounts/{accountId}")
    public Map<String, Object> cashBackByAccountId(@PathVariable String accountId) {
        return cashbackService.getCashbackDetailsForAccount(accountId);
    }

}
