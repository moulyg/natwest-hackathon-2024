package com.natwest.boa.hackathon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PispConfig {

    @Value("${pisp.target.context}")
    private String pispContext;

    @Value("${pisp.target.baseUri}")
    private String pispBaseUri;

    @Value("${pisp.target.audience}")
    private String pispAudience;

    public String getPispContext() {
        return pispContext;
    }

    public String getPispBaseUri() {
        return pispBaseUri;
    }

    public String getPispAudience() {
        return pispAudience;
    }
}