package com.hust.market.config;

import com.hust.market.service.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketConfig {

    @Bean
    public Utils utils(){
        return new Utils();
    }
}
