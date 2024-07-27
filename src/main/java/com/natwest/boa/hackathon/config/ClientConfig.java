package com.natwest.boa.hackathon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientConfig {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${client.redirectUri}")
    private String redirectUri;

    @Value("${client.state}")
    private String clientState;

    @Value("${client.authorizationUsername}")
    private String authorizationUsername;

    @Value("${client.authorizationAccount}")
    private String authorizationAccount;

    @Value("${client.financialId}")
    private String financialId;

    @Value("${client.tokenUrl}")
    private String tokenUrl;

    @Value("${client.initRunning:false}")
    private boolean initRunning;

    @Value("${client.jwsSignature}")
    private String jwsSignature;

    @Value("${client.idempotencyKey}")
    private String idempotencyKey;


    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getClientState() {
        return clientState;
    }

    public String getAuthorizationUsername() {
        return authorizationUsername;
    }

    public String getAuthorizationAccount() {
        return authorizationAccount;
    }

    public String getFinancialId() {
        return financialId;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public boolean isInitRunning() {
        return initRunning;
    }

    public String getJwsSignature() { return jwsSignature; }

    public String getIdempotencyKey() { return idempotencyKey; }
}