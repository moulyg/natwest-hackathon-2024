package com.natwest.boa.hackathon.config;

import com.natwest.boa.hackathon.service.remote.PaymentRemote;
import com.natwest.boa.hackathon.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RemoteContext.class, PispContext.class})
public class RootContext {
    @Bean
    public PaymentService pispService(PaymentRemote pispRemote,
                                      ClientConfig clientConfig)  {
        return new PaymentService(pispRemote, clientConfig);

    }
}