package com.example.demo.config;

import com.example.demo.util.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configBean {

    @Bean public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}
