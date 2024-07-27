package com.natwest.boa.hackathon.service;

import com.natwest.boa.hackathon.model.token.TokenRequest;
import com.natwest.boa.hackathon.model.token.TokenResponse;
import com.natwest.boa.hackathon.config.ClientConfig;
import com.natwest.boa.hackathon.service.remote.TokenRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private TokenRemote tokenRemote;
    private ClientConfig clientConfig;

    public TokenService(TokenRemote tokenRemote, ClientConfig clientConfig) {
        this.tokenRemote = tokenRemote;
        this.clientConfig = clientConfig;
    }

    public TokenResponse getTokenResponse() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClientId(clientConfig.getClientId());
        tokenRequest.setClientSecret(clientConfig.getClientSecret());
        tokenRequest.setGrantType("client_credentials");
        tokenRequest.setScope("payments");
      try {
          return tokenRemote.generateToken(tokenRequest);
      } catch (HttpClientErrorException ex) {
          logger.error(ex.getResponseBodyAsString(), ex);
          throw ex;
      }
    }

    public TokenResponse exchangeAuthorizationCode(String code) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClientId(clientConfig.getClientId());
        tokenRequest.setClientSecret(clientConfig.getClientSecret());
        tokenRequest.setRedirectUri(clientConfig.getRedirectUri());
        tokenRequest.setGrantType("authorization_code");
        tokenRequest.setCode(code);
        try {
            return tokenRemote.generateToken(tokenRequest);
        } catch (HttpClientErrorException ex) {
            logger.error(ex.getResponseBodyAsString(), ex);
            throw ex;
        }
    }

}