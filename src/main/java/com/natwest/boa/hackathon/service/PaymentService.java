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



@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private PaymentRemote pispRemote;
    private TokenRemote tokenRemote;
    private ClientConfig clientConfig;
    private ObjectMapper mapper;

    public PaymentService(PaymentRemote pispRemote, TokenRemote tokenRemote, ClientConfig clientConfig) {
        this.pispRemote = pispRemote;
        this.tokenRemote = tokenRemote;
        this.clientConfig = clientConfig;
        mapper = new ObjectMapper();
    }
    public OBWriteDomesticConsentResponse createPaymentConsent(OBWriteDomesticConsent obWriteDomesticConsent2) {
        //first access token
        String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw";
        return pispRemote.createPaymentConsent(obWriteDomesticConsent2, setHeader(accessToken));
    }

    public String createAuthorizeUri(String consentId) {
        return pispRemote.createAuthorizeUri(consentId);
    }

    public OBWriteDomesticResponse createDomesticPayment(OBWriteDomestic obWriteDomestic) {

        try {
            //second access token which used to make payment
            String newAccessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw";
            return pispRemote.createDomesticPayment(obWriteDomestic, setHeader(newAccessToken));
        } catch (HttpStatusCodeException ex) {
            logger.error(ex.getResponseBodyAsString(), ex);
            throw ex;
        }
    }

    public OBWriteDomesticResponse getPaymentStatus(String paymentId) {
        try {
            //second access token which used to check payment status
            String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw";
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
        httpRequestHeader.setIdempotencyKey(clientConfig.getIdempotencyKey());

        return httpRequestHeader;
    }


}