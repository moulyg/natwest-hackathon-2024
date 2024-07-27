package com.natwest.boa.hackathon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ TokenContext.class, ClientConfig.class})
public class RemoteContext {

}