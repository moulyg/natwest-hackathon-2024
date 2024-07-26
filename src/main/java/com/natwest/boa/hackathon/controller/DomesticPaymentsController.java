package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.model.OBWriteDataDomesticResponse;
import com.natwest.boa.hackathon.model.OBWriteDomestic;
import com.natwest.boa.hackathon.service.DomesticPaymentsService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
public class DomesticPaymentsController {

    private DomesticPaymentsService domesticPaymentsService;

    public DomesticPaymentsController(DomesticPaymentsService domesticPaymentsService) {
        this.domesticPaymentsService = domesticPaymentsService;
    }

    @PostMapping("/domestic-payments")
    public OBWriteDataDomesticResponse domesticPayments(@RequestBody OBWriteDomestic obWriteDomestic) {
        return domesticPaymentsService.makeDomesticPayment(obWriteDomestic);
    }

}
