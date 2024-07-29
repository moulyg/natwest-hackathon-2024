package com.natwest.boa.hackathon.service.remote;

import com.natwest.boa.hackathon.model.common.HttpRequestHeader;
import com.natwest.boa.hackathon.model.payments.OBWriteDomestic;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsent;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticConsentResponse;
import com.natwest.boa.hackathon.model.payments.OBWriteDomesticResponse;
import com.natwest.boa.hackathon.controller.Endpoints;
import com.natwest.boa.hackathon.util.PispUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentRemote {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRemote.class);

    private final RestTemplate sslRestTemplate;
    private final PispUtils pispUtil;

    public PaymentRemote(RestTemplate restTemplate, PispUtils pispUtil) {
        this.sslRestTemplate = restTemplate;
        this.pispUtil = pispUtil;
    }

    public OBWriteDomesticConsentResponse createPaymentConsent(OBWriteDomesticConsent obWriteDomesticConsent2, HttpRequestHeader httpRequestHeader) {
        System.out.println(httpRequestHeader.getAuthorization());
        System.out.println(httpRequestHeader.getFinancialId());
        return sslRestTemplate.exchange(
                pispUtil.getUri(Endpoints.DOMESTIC_PAYMENT_CONSENTS_ENDPOINT),
                HttpMethod.POST,
                pispUtil.createRequest(obWriteDomesticConsent2, httpRequestHeader),
                OBWriteDomesticConsentResponse.class).getBody();

    }

    public String createAuthorizeUri(String consentId, String state) {
       return pispUtil.createAuthorizeUrl(consentId, state);
    }

    public OBWriteDomesticResponse createDomesticPayment(OBWriteDomestic obWriteDomestic2, HttpRequestHeader httpRequestHeader) {
        System.out.println(httpRequestHeader);
        return sslRestTemplate.exchange(
                pispUtil.getUri(Endpoints.DOMESTIC_PAYMENTS_ENDPOINT),
                HttpMethod.POST,
                pispUtil.createRequest(obWriteDomestic2, httpRequestHeader),
                OBWriteDomesticResponse.class).getBody();

    }

    public OBWriteDomesticResponse getPaymentStatus(String paymentId,  HttpRequestHeader httpRequestHeader) {

        return  sslRestTemplate.exchange(
                pispUtil.getUri(Endpoints.DOMESTIC_PAYMENTS_PAYMENT_ID_ENDPOINT),
                HttpMethod.GET,
                pispUtil.createRequest(null, httpRequestHeader),
                OBWriteDomesticResponse.class,
                paymentId).getBody();

    }
}