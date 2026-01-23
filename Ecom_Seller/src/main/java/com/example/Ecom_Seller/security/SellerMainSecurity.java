package com.example.Ecom_Seller.security;

import com.example.Ecom_Seller.filter.JWT_Generation;
import com.example.Ecom_Seller.filter.JWT_Validation;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SellerMainSecurity {

    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        Cors_PasswordEncode corsPasswordEncode = new Cors_PasswordEncode();
        httpSecurity.cors(x->corsPasswordEncode.corsConfigurationSource());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(x->x.requestMatchers("/seller/**",
                                                       "/seller/see/seller",
                                                       "/seller/save/details",
                                                       "/seller/delete",
                                                       "/seller/update/seller/address",
                                                       "/seller/update/seller/email")
                                               .authenticated()
                                               .requestMatchers("/seller/generate/jwt","/seller/**")
                                               .permitAll()
                                               .anyRequest()
                                               .authenticated());
        httpSecurity.addFilterAfter(new JWT_Generation(), BasicAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new JWT_Validation(), BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
