package com.scaler.ProductService.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        //For 3rd party api access
        return new RestTemplate();
    }

}
