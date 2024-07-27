package com.natwest.boa.hackathon.service.remote;

import com.natwest.boa.hackathon.model.token.TokenRequest;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import com.natwest.boa.hackathon.config.ClientConfig;
import com.natwest.boa.hackathon.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenRemote {

    private static final Logger logger = LoggerFactory.getLogger(TokenRemote.class);

    private RestTemplate securedRestTemplate;
    private TokenUtils tokenUtil;
    private ClientConfig clientConfig;


    public TokenRemote(RestTemplate securedRestTemplate, TokenUtils tokenUtil, ClientConfig clientConfig) {
        this.securedRestTemplate = securedRestTemplate;
        this.tokenUtil = tokenUtil;
        this.clientConfig = clientConfig;
    }

    public TokenResponse generateToken(TokenRequest requestToken) {

            ResponseEntity<TokenResponse> accessTokenResponse = securedRestTemplate.postForEntity(clientConfig.getTokenUrl(),
                    tokenUtil.createTokenObject(requestToken), TokenResponse.class);

            return accessTokenResponse.getBody();

    }
}