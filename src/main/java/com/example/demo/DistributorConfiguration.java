package com.example.demo;

import com.example.demo.logics.PersonGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributorConfiguration {

    @Bean
    public PersonGenerator personGenerator(){
        return new PersonGenerator();
    }
}
