package com.home.ihm.demo;

import com.home.ihm.demo.service.AdvertiserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class AdvertiserConfig {

    @Bean
    AdvertiserService advertiserService() {
        return new AdvertiserService();
    }
}

