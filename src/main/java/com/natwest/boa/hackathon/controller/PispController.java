package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsent;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsentResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticResponse;
import com.natwest.boa.hackathon.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.natwest.boa.hackathon.controller.Endpoints.*;

@RestController
@RequestMapping("/open-banking/*/pisp")
public class PispController {

    private PaymentService pispService;

    @Autowired
    public PispController(PaymentService pispService) {
        this.pispService = pispService;
    }

    @PostMapping(value = DOMESTIC_PAYMENT_CONSENTS_ENDPOINT)
    public ResponseEntity<OBWriteDomesticConsentResponse> paymentConsentSetup(@RequestBody OBWriteDomesticConsent obWriteDomesticConsent2) {
        OBWriteDomesticConsentResponse obWriteDataDomesticConsentResponse2 = pispService.createPaymentConsent(obWriteDomesticConsent2);
        return new ResponseEntity<>(obWriteDataDomesticConsentResponse2, HttpStatus.CREATED);
    }

    @GetMapping(value = AUTHORIZATION_OAUTH2_ENDPOINT)
    public String pispConsentAuthUrl(@PathVariable("consentId") String consentId) {
        return pispService.createAuthorizeUri(consentId.toString());
    }

    @PostMapping(value = DOMESTIC_PAYMENTS_ENDPOINT)
    public ResponseEntity<OBWriteDomesticResponse> submitPayments(@RequestBody(required = false) OBWriteDomestic obWriteDomestic2) throws Exception {
        OBWriteDomesticResponse paymentsSubmit = pispService.createDomesticPayment(obWriteDomestic2);
        return new ResponseEntity<>(paymentsSubmit, HttpStatus.CREATED);
    }

    @GetMapping(value = DOMESTIC_PAYMENTS_PAYMENT_ID_ENDPOINT)
    public OBWriteDomesticResponse getPaymentStatus(@PathVariable(value = "paymentId") String paymentId) {
        return pispService.getPaymentStatus(paymentId);
    }
}