package com.natwest.boa.hackathon.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.boa.hackathon.model.payments.*;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import com.natwest.boa.hackathon.service.PaymentService;
import com.natwest.boa.hackathon.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class PaymentController {

    private TokenService tokenService;

    private PaymentService pispService;
    private ObjectMapper mapper;

    @Autowired
    public PaymentController(TokenService tokenService, PaymentService pispService) {
        this.tokenService = tokenService;
        this.pispService = pispService;
        mapper = new ObjectMapper();
    }

    @RequestMapping(
            value = "/open-banking/v3/payment-submit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView paymentSubmission() {
        TokenResponse tokenResponse = tokenService.getTokenResponse();


        OBWriteDomesticConsent obWriteDomesticConsent2 = new OBWriteDomesticConsent();
        OBWriteDataDomesticConsent data = new OBWriteDataDomesticConsent();
        OBDomestic initiation = new OBDomestic();
        OBRisk risk = new OBRisk();
        //set risk
        risk.setPaymentContextCode(OBExternalPaymentContextCode.ECOMMERCEGOODS);
        obWriteDomesticConsent2.setRisk(risk);
        // set data
        obWriteDomesticConsent2.setData(data);
        data.setInitiation(initiation);
        //set indeifitcation ids
        initiation.setInstructionIdentification("instr-identification");
        initiation.setEndToEndIdentification("e2e-identification");
        //set Amount
        OBDomesticInstructedAmount amount = new OBDomesticInstructedAmount();
        amount.setAmount("50.00");
        amount.setCurrency("GBP");
        initiation.setInstructedAmount(amount);

        // Creditor Account
        OBCashAccountCreditor creditorAccount = new OBCashAccountCreditor();
        creditorAccount.setSchemeName("IBAN");
        creditorAccount.setIdentification("BE56456394728288");
        creditorAccount.setName("ACME DIY");
        creditorAccount.setSecondaryIdentification("secondary-identif");
        initiation.setCreditorAccount(creditorAccount);


        OBRemittanceInformation remittanceInformation = new OBRemittanceInformation();
        remittanceInformation.setReference("Tools");
        remittanceInformation.setUnstructured("Tools");
        initiation.setRemittanceInformation(remittanceInformation);

        OBWriteDomesticConsentResponse obWriteDataDomesticConsentResponse2 = pispService.createPaymentConsent(obWriteDomesticConsent2, tokenResponse.getAccessToken());
        String consentId = obWriteDataDomesticConsentResponse2.getData().getConsentId();
        String authorizeUri = pispService.createAuthorizeUri(consentId);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl(authorizeUri);
        return rv;
    }

    @RequestMapping(
            value = "/open-banking/v3/payment-redirection{parameters}",
            method = RequestMethod.GET)
    public RedirectView paymentRedirection(@PathVariable String parameters, HttpServletRequest httpServletRequest)

    {

        System.out.println("redirection completed = " + parameters + httpServletRequest.getRequestURI());
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("http://localhost:3000/#/result");
        return rv;
    }
}
