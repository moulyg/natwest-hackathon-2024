package com.natwest.boa.hackathon.controller;

import com.natwest.boa.hackathon.model.token.TokenResponse;
import com.natwest.boa.hackathon.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private TokenService tokenService;
    private ObjectMapper mapper;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
        mapper = new ObjectMapper();
    }

    @PostMapping(value = "/open-banking/v3/token")
    public TokenResponse generateToken() {
        return tokenService.getTokenResponse();
    }

    @PostMapping(value = "/open-banking/v3/tokenExchange/{code}")
    public TokenResponse exchangeToken(@PathVariable(value = "code") String code) {
        return tokenService.exchangeAuthorizationCode(code);
    }
}