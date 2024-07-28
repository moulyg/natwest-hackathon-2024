package com.natwest.boa.hackathon.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.natwest.boa.hackathon.model.consent.Consent;
import com.natwest.boa.hackathon.model.payments.*;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import com.natwest.boa.hackathon.service.ConsentService;
import com.natwest.boa.hackathon.service.PaymentService;
import com.natwest.boa.hackathon.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private TokenService tokenService;

    private PaymentService pispService;

    private ConsentService consentService;
    private ObjectMapper mapper;

    @Autowired
    public PaymentController(TokenService tokenService, PaymentService pispService, ConsentService consentService) {
        this.tokenService = tokenService;
        this.pispService = pispService;
        this.consentService = consentService;
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @RequestMapping(
            value = "/open-banking/v3/payment-submit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView paymentSubmission() throws JsonProcessingException {
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

        // TODO save obWriteDomesticConsent2 against consentId in session or cache. to retrive later
        Consent storedConsent = consentService.saveConsent(consentId, mapper.writeValueAsString(obWriteDataDomesticConsentResponse2));

        logger.info("Saved Consent Details : " + storedConsent.toString());


        String authorizeUri = pispService.createAuthorizeUri(consentId, storedConsent.getId().toString());
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl(authorizeUri);
        return rv;
    }

    @RequestMapping(
            value = "/open-banking/v3/payment-redirection{parameters}",
            method = RequestMethod.GET)
    public RedirectView paymentRedirection(@PathVariable String parameters, HttpServletRequest httpServletRequest) {

        System.out.println("redirection completed = " + parameters + httpServletRequest.getRequestURI());
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("http://localhost:3000/#/result");
        return rv;
    }
}
