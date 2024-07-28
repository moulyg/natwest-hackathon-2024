package com.natwest.boa.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticResponse;
import com.natwest.boa.hackathon.service.DomesticPaymentsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
    public OBWriteDomesticResponse domesticPayments(@RequestBody Map<String, String> requestBody) throws JsonProcessingException, InterruptedException {
        return domesticPaymentsService.makeDomesticPayment(requestBody);
    }

    @RequestMapping(
            value = "/domestic-payments-consents",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView paymentSubmission() throws JsonProcessingException {
        return domesticPaymentsService.submitDomesticPaymentsConsent();
    }

//    @RequestMapping(
//            value = "/payment-redirection{parameters}",
//            method = RequestMethod.GET)
//    public RedirectView paymentRedirection(@PathVariable String parameters, HttpServletRequest httpServletRequest) {
//        return domesticPaymentsService.redirection(parameters, httpServletRequest);
//    }
}
