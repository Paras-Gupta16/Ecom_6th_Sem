package com.example.Ecom_Project.service;

import com.example.Ecom_Project.security.CustomerSecurity;
import com.example.Ecom_Project.utility.LoadUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomerLoginService implements AuthenticationProvider {

    private final LoadUserDetails loadUserDetails;
    private final CustomerSecurity customerSecurity;

    @Value("${app.security.pepper}")
    private String salt;

    public CustomerLoginService(
            LoadUserDetails loadUserDetails,
            CustomerSecurity customerSecurity
    ) {
        this.loadUserDetails = loadUserDetails;
        this.customerSecurity = customerSecurity;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails =
                loadUserDetails.loadUserByUsername(username);

        if (customerSecurity.passwordEncoder()
                            .matches(password+salt, userDetails.getPassword())) {

            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        }

        throw new BadCredentialsException("Invalid Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

