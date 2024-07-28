package com.natwest.boa.hackathon.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.natwest.boa.hackathon.model.consent.Consent;
import com.natwest.boa.hackathon.model.cashback.Cashback;
import com.natwest.boa.hackathon.model.payments.OBCashAccountCreditor;
import com.natwest.boa.hackathon.model.payments.OBDomestic;
import com.natwest.boa.hackathon.model.payments.OBDomesticInstructedAmount;
import com.natwest.boa.hackathon.model.payments.OBExternalPaymentContextCode;
import com.natwest.boa.hackathon.model.payments.OBRemittanceInformation;
import com.natwest.boa.hackathon.model.payments.OBRisk;
import com.natwest.boa.hackathon.model.payments.OBTransactionIndividualStatusCode;
import com.natwest.boa.hackathon.model.payments.OBWriteDataDomesticConsent;
import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsent;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsentResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticResponse;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Objects;

@org.springframework.stereotype.Service
public class DomesticPaymentsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomesticPaymentsService.class);

    @Autowired
    private CashbackService cashbackService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PaymentService pispService;

    @Autowired
    private ConsentService consentService;

    private final ObjectMapper mapper;

    public DomesticPaymentsService() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    public OBWriteDomesticResponse makeDomesticPayment(Map<String, String> body) throws JsonProcessingException, InterruptedException {
        String code = body.get("code");
        String state = body.get("state");
        Thread.sleep(2000);
        TokenResponse tokenResponse = tokenService.exchangeAuthorizationCode(code);

        Consent consentById = consentService.getConsentById(Long.parseLong(state));
        String consentData = consentById.getConsentData();
        String consentId = consentById.getConsentId();
        OBWriteDomestic obWriteDomestic = mapper.readValue(consentData, OBWriteDomestic.class);
        obWriteDomestic.getData().setConsentId(consentId);
        OBWriteDomesticResponse paymentsSubmit = pispService.createDomesticPayment(obWriteDomestic, tokenResponse.getAccessToken());

        String sustainableProductsAmount = body.get("sustainableProductsAmount");
        if (isPaymentSuccessful(paymentsSubmit.getData().getStatus()) && Objects.nonNull(sustainableProductsAmount)) {
            //Call Cashback API
            Cashback cashbackAmount = cashbackService.calculateRewardCashBack(Double.parseDouble(sustainableProductsAmount), paymentsSubmit.getData().getDomesticPaymentId(), paymentsSubmit.getData().getDebtor().getIdentification());
            LOGGER.info("Received cashback of " + cashbackAmount.toString());
        }
        return paymentsSubmit;
    }

    private boolean isPaymentSuccessful(OBTransactionIndividualStatusCode status) {
        return OBTransactionIndividualStatusCode.PENDING == status || OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTCOMPLETED == status || OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTINPROCESS == status;
    }


    public RedirectView submitDomesticPaymentsConsent() throws JsonProcessingException {

        //Token Service
        TokenResponse tokenResponse = tokenService.getTokenResponse();

        OBWriteDomesticConsent obWriteDomesticConsent2 = getObWriteDomesticConsent();

        //Create Payment Consents
        OBWriteDomesticConsentResponse obWriteDataDomesticConsentResponse2 = pispService.createPaymentConsent(obWriteDomesticConsent2, tokenResponse.getAccessToken());

        String consentId = obWriteDataDomesticConsentResponse2.getData().getConsentId();

        //Consent storing in H2
        Consent storedConsent = consentService.saveConsent(consentId, mapper.writeValueAsString(obWriteDataDomesticConsentResponse2));

        LOGGER.info("Saved Consent Details : " + storedConsent.toString());

        String authorizeUri = pispService.createAuthorizeUri(consentId, storedConsent.getId().toString());
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl(authorizeUri);
        return rv;
    }

    private static OBWriteDomesticConsent getObWriteDomesticConsent() {
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
        //set identification ids
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
        return obWriteDomesticConsent2;
    }

    public RedirectView redirection(String parameters, HttpServletRequest httpServletRequest){
        LOGGER.info("redirection completed = " + parameters + httpServletRequest.getRequestURI());
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("http://localhost:3000/#/result");
        return redirectView;
    }
}
