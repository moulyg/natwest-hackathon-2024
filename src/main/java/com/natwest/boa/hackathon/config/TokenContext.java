package com.natwest.boa.hackathon.config;

import com.natwest.boa.hackathon.service.remote.TokenRemote;
import com.natwest.boa.hackathon.util.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TokenContext {


    @Bean
    public TokenRemote tokenRemote(RestTemplate securedRestTemplate,
                                   TokenUtils tokenUtil,
                                   ClientConfig clientConfig) {
        return new TokenRemote(securedRestTemplate, tokenUtil, clientConfig);
    }

    @Bean
    public TokenUtils tokenUtil() {
        return new TokenUtils();
    }

}