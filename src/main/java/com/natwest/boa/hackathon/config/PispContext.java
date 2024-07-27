package com.natwest.boa.hackathon.config;

import com.natwest.boa.hackathon.service.remote.PaymentRemote;
import com.natwest.boa.hackathon.util.PispUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PispContext {

    @Bean
    public PispConfig pispConfig(){
        return new PispConfig();
    }

    @Bean
    public PispUtils pispUtil(PispConfig pispConfig, ClientConfig clientConfig) {
        return new PispUtils(pispConfig, clientConfig);
    }

    @Bean
    public PaymentRemote paymentRemote(RestTemplate securedRestTemplate,
                                 PispUtils pispUtil) {
        return  new PaymentRemote(securedRestTemplate, pispUtil);
    }
}