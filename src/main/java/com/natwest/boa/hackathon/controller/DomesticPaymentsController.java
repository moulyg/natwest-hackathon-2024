package com.natwest.boa.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.natwest.boa.hackathon.model.payments.OBWriteDataDomesticResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.service.DomesticPaymentsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class DomesticPaymentsController {

    private final DomesticPaymentsService domesticPaymentsService;

    public DomesticPaymentsController(DomesticPaymentsService domesticPaymentsService) {
        this.domesticPaymentsService = domesticPaymentsService;
    }

    @PostMapping("/domestic-payments")
    @CrossOrigin
    public OBWriteDataDomesticResponse domesticPayments(@RequestBody Map<String, String> body) throws JsonProcessingException, InterruptedException {
        System.out.println(body);
        // TODO retrive the saved domestic payment request (OBWriteDomestic) from session or cache
        return domesticPaymentsService.makeDomesticPayment(body);
    }

}
