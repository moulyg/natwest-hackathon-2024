package com.natwest.boa.hackathon.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.natwest.boa.hackathon.model.consent.Consent;
import com.natwest.boa.hackathon.model.payments.*;
import com.natwest.boa.hackathon.model.cashback.Cashback;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@org.springframework.stereotype.Service
public class DomesticPaymentsService {

    private static final Logger logger = LoggerFactory.getLogger(DomesticPaymentsService.class);

    @Autowired
    private CashbackService cashbackService;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private PaymentService pispService;

    @Autowired
    private ConsentService consentService;

    private ObjectMapper mapper;

    public DomesticPaymentsService() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    public OBWriteDataDomesticResponse makeDomesticPayment(Map<String, String> body) throws JsonProcessingException, InterruptedException {
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
            logger.info("Received cashback of "+cashbackAmount.toString());
        }
        return buildOBWriteDataDomesticResponse();
    }

    private boolean isPaymentSuccessful(OBTransactionIndividualStatusCode status) {
        return OBTransactionIndividualStatusCode.PENDING == status || OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTCOMPLETED == status || OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTINPROCESS == status;
    }


    private OBWriteDataDomesticResponse buildOBWriteDataDomesticResponse() {


        OBWriteDataDomesticResponse obWriteDataDomesticResponse = new OBWriteDataDomesticResponse();
        obWriteDataDomesticResponse.setDomesticPaymentId(UUID.randomUUID().toString());
        obWriteDataDomesticResponse.setConsentId(UUID.randomUUID().toString());
        obWriteDataDomesticResponse.setCreationDateTime(OffsetDateTime.now());
        obWriteDataDomesticResponse.setStatus(OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTINPROCESS);
        obWriteDataDomesticResponse.setStatusUpdateDateTime(OffsetDateTime.now());

        OBDomestic obDomestic = getObDomestic();


        obWriteDataDomesticResponse.setInitiation(obDomestic);

        OBMultiAuthorisation obMultiAuthorisation = new OBMultiAuthorisation();
        obWriteDataDomesticResponse.setMultiAuthorisation(obMultiAuthorisation);

        return obWriteDataDomesticResponse;
    }

    private static OBDomestic getObDomestic() {
        OBDomestic obDomestic = new OBDomestic();
        obDomestic.setInstructionIdentification("1234567");
        obDomestic.setEndToEndIdentification("EndToEndIdentification");
        obDomestic.setLocalInstrument("UK.OBIE.FPS");
        OBDomesticInstructedAmount obDomesticInstructedAmount = new OBDomesticInstructedAmount();
        obDomesticInstructedAmount.setAmount("100");
        obDomesticInstructedAmount.setCurrency("GBP");
        obDomestic.setInstructedAmount(obDomesticInstructedAmount);
        return obDomestic;
    }
}
