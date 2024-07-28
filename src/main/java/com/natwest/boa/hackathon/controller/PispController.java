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
        OBWriteDomesticConsentResponse obWriteDataDomesticConsentResponse2 = pispService.createPaymentConsent(obWriteDomesticConsent2, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw");
        return new ResponseEntity<>(obWriteDataDomesticConsentResponse2, HttpStatus.CREATED);
    }

    @GetMapping(value = AUTHORIZATION_OAUTH2_ENDPOINT)
    public String pispConsentAuthUrl(@PathVariable("consentId") String consentId) {
        return pispService.createAuthorizeUri(consentId, null);
    }

    @PostMapping(value = DOMESTIC_PAYMENTS_ENDPOINT)
    public ResponseEntity<OBWriteDomesticResponse> submitPayments(@RequestBody(required = false) OBWriteDomestic obWriteDomestic2) throws Exception {
        OBWriteDomesticResponse paymentsSubmit = pispService.createDomesticPayment(obWriteDomestic2, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw");
        return new ResponseEntity<>(paymentsSubmit, HttpStatus.CREATED);
    }

    @GetMapping(value = DOMESTIC_PAYMENTS_PAYMENT_ID_ENDPOINT)
    public OBWriteDomesticResponse getPaymentStatus(@PathVariable(value = "paymentId") String paymentId) {
        return pispService.getPaymentStatus(paymentId, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOiJkZW1vLWFwcC1lNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYiLCJvcmciOiJlNGNiNjY4Ny01ZDhmLTRlMzQtYjMwNC02Yzc0ZWRlNzQ0ODYuZXhhbXBsZS5vcmciLCJpc3MiOiJodHRwczovL2FwaS5zYW5kYm94Lm5hdHdlc3QuY29tIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImV4dGVybmFsX2NsaWVudF9pZCI6IlZjZzVhZVVsR08xalJ0cUdsdkNfWV9sMERmUFFibFAxNURjckNPVnNPNlk9IiwiY2xpZW50X2lkIjoiZmFhNTk4ZmUtMjI0Mi00Njc4LWFmN2YtNGZmZTljNTc5NDc1IiwibWF4X2FnZSI6ODY0MDAsImF1ZCI6ImZhYTU5OGZlLTIyNDItNDY3OC1hZjdmLTRmZmU5YzU3OTQ3NSIsInNjb3BlIjoicGF5bWVudHMiLCJleHAiOjE3MjIwNzE3NDIsImlhdCI6MTcyMjA3MTE0MiwianRpIjoiMjhjYWZhZjItNDU4Mi00ZTUxLTg3MGEtNjIwNDIzYzQ0NmYzIiwidGVuYW50IjoiTmF0V2VzdCJ9.mQC3WkeelFwuciPocAkGlMzyNvOo9RkJnzKiuLglsIgPy_iqhuarXm9lT4nyvrseEXLHEO0GMqKtNQXn6gVRRdjFz_4Laee8x6f8EBkqq0PnYTfYJQimLJKRrBKb7iwuG8v1VTe4kBnDNpBWo1ZODuyLo2ssgr2LQbOcFarVm4GMTw-ZCMKyEaHllCwGgvslRY3nfTa17xeDEg1xm6p5bkIPFQaAaLV-dQDAhysE6i-g1sNS03VW9mIC8ETu1pCmLffE8Jv1Dj76DZ76pVglJ0mH7xiWNtrflYoEQ9tEX_xzP2jm9KMFon-xSt5vtrXBA6JSke-XKaH21x3Oy4LMfw");
    }
}