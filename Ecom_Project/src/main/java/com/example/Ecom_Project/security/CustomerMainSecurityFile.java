package com.example.Ecom_Project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class CustomerMainSecurityFile {

    CustomerSecurity customerSecurity = new CustomerSecurity();

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CorsConfigurationSource source =  customerSecurity.corsConfigurationSource();
        httpSecurity.cors(x-> x.configurationSource(source));

        httpSecurity.authorizeHttpRequests(x->x.
                requestMatchers("/customer/**",
                        "/customer/save/details",
                        "/customer/getDetails/email",
                        "/customer/delete/email")
                .permitAll()
                .anyRequest()
                .authenticated());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }
}
