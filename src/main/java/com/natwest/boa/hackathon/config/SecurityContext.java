package com.natwest.boa.hackathon.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class SecurityContext {

    @Autowired
    private SecurityConfig securityConfig;

    @Bean
     public RestTemplate getRestTemplate() throws Exception{

        /*
         * Create a RestTemplate that bypass ssl verification
         */

         SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                 .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                 .build();

         SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

         CloseableHttpClient httpClient = HttpClients.custom()
                 .setConnectionManager(
                         PoolingHttpClientConnectionManagerBuilder.create()
                                 .setSSLSocketFactory(csf)
                                 .build())
                 .build();

         HttpComponentsClientHttpRequestFactory requestFactory =
                 new HttpComponentsClientHttpRequestFactory();

         requestFactory.setHttpClient(httpClient);

         return  new RestTemplate(requestFactory);
    }

}