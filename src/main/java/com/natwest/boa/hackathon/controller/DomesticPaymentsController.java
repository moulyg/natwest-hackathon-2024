package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.model.payments.OBWriteDataDomesticResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.service.DomesticPaymentsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class DomesticPaymentsController {

    private final DomesticPaymentsService domesticPaymentsService;

    public DomesticPaymentsController(DomesticPaymentsService domesticPaymentsService) {
        this.domesticPaymentsService = domesticPaymentsService;
    }

    @PostMapping("/domestic-payments")
    public OBWriteDataDomesticResponse domesticPayments(@RequestBody OBWriteDomestic obWriteDomestic) {

        // TODO retrive the saved domestic payment request (OBWriteDomestic) from session or cache
        return domesticPaymentsService.makeDomesticPayment(obWriteDomestic);
    }

}
