package com.natwest.boa.hackathon.service;

import com.natwest.boa.hackathon.model.common.HttpRequestHeader;
import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsent;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsentResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticResponse;
import com.natwest.boa.hackathon.config.ClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.boa.hackathon.service.remote.PaymentRemote;
import com.natwest.boa.hackathon.service.remote.TokenRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;


@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRemote pispRemote;
    private final ClientConfig clientConfig;

    public PaymentService(PaymentRemote pispRemote, ClientConfig clientConfig) {
        this.pispRemote = pispRemote;
        this.clientConfig = clientConfig;
    }
    public OBWriteDomesticConsentResponse createPaymentConsent(OBWriteDomesticConsent obWriteDomesticConsent2, String accessToken) {
        //first access token
        return pispRemote.createPaymentConsent(obWriteDomesticConsent2, setHeader(accessToken));
    }

    public String createAuthorizeUri(String consentId, String state) {
        return pispRemote.createAuthorizeUri(consentId, state);
    }

    public OBWriteDomesticResponse createDomesticPayment(OBWriteDomestic obWriteDomestic, String accessToken) {

        try {
            //second access token which used to make payment
            return pispRemote.createDomesticPayment(obWriteDomestic, setHeader(accessToken));
        } catch (HttpStatusCodeException ex) {
            logger.error(ex.getResponseBodyAsString(), ex);
            throw ex;
        }
    }

    public OBWriteDomesticResponse getPaymentStatus(String paymentId, String accessToken) {
        try {
            //second access token which used to check payment status
            return pispRemote.getPaymentStatus(paymentId, setHeader(accessToken));
        } catch (HttpStatusCodeException ex) {
            logger.error(ex.getResponseBodyAsString(), ex);
            throw ex;
        }
    }

    private HttpRequestHeader setHeader(String accessToken) {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();

        httpRequestHeader.setAuthorization("Bearer " + accessToken);
        httpRequestHeader.setFinancialId(clientConfig.getFinancialId());
        httpRequestHeader.setJwsSignature(clientConfig.getJwsSignature());
        httpRequestHeader.setIdempotencyKey(UUID.randomUUID().toString());

        return httpRequestHeader;
    }


}