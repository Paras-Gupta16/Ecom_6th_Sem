package com.example.Ecom_Project.security;

import com.example.Ecom_Project.filters.JWT_GenerationFilter;
import com.example.Ecom_Project.filters.JWT_ValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
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
                requestMatchers("/jwt/token/generation","/jwt/**","/customer/save/details")
                .permitAll()
                .requestMatchers("/customer/getDetails/email",
                        "/customer/delete/email","/customer/**","/customer/health")
                .authenticated()
                .anyRequest()
                .authenticated());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.addFilterAfter(new JWT_GenerationFilter(), BasicAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new JWT_ValidationFilter(),BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
