package com.natwest.boa.hackathon.service;


import com.natwest.boa.hackathon.model.OBDomestic;
import com.natwest.boa.hackathon.model.OBDomesticInstructedAmount;
import com.natwest.boa.hackathon.model.OBMultiAuthorisation;
import com.natwest.boa.hackathon.model.OBTransactionIndividualStatusCode;
import com.natwest.boa.hackathon.model.OBWriteDataDomesticResponse;
import com.natwest.boa.hackathon.model.OBWriteDomestic;
import com.natwest.boa.hackathon.model.cashback.Cashback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@org.springframework.stereotype.Service
public class DomesticPaymentsService {

    private static final Logger logger = LoggerFactory.getLogger(DomesticPaymentsService.class);

    @Autowired
    private CashbackService cashbackService;

    public OBWriteDataDomesticResponse makeDomesticPayment(OBWriteDomestic obWriteDomestic) {

        //call domestic-payments API
        OBWriteDataDomesticResponse obWriteDataDomesticResponse = new OBWriteDataDomesticResponse();
        obWriteDataDomesticResponse.setStatus(OBTransactionIndividualStatusCode.ACCEPTEDSETTLEMENTINPROCESS);
        obWriteDataDomesticResponse.domesticPaymentId(UUID.randomUUID().toString());

        if (isPaymentSuccessful(obWriteDataDomesticResponse.getStatus()) && Objects.nonNull(obWriteDomestic.getData().getInitiation().getSustainableProductsAmount())) {
            //Call Cashback API
            Cashback cashbackAmount = cashbackService.calculateRewardCashBack(Double.parseDouble(obWriteDomestic.getData().getInitiation().getSustainableProductsAmount()), obWriteDataDomesticResponse.getDomesticPaymentId(), obWriteDomestic.getData().getInitiation().getDebtorAccount().getIdentification());
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
