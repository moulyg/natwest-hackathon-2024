package com.natwest.boa.hackathon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {

    @Value("${tls.enableMatls}")
    private boolean enableMatls;

    @Value("${tls.keyStore.type:JKS}")
    private String keyStoreType;

    @Value("${tls.keyStore.location}")
    private String keyStoreLocation;

    @Value("${tls.keyStore.password}")
    private String keyStorePassword;

    @Value("${tls.trustStore.location}")
    private String trustStoreLocation;

    @Value("${tls.trustStore.password}")
    private String trustStorePassword;

    public boolean isEnableMatls() { return enableMatls; }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public String getKeyStoreLocation() {
        return keyStoreLocation;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getTrustStoreLocation() {
        return trustStoreLocation;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }
}