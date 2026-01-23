package com.example.Ecom_Seller.service;

import com.example.Ecom_Seller.security.Cors_PasswordEncode;
import com.example.Ecom_Seller.utility.LoadUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SellerLoginService implements AuthenticationProvider {

    private final LoadUserDetails loadUserDetails;
    private final Cors_PasswordEncode corsPasswordEncode;

    public SellerLoginService(LoadUserDetails loadUserDetails, Cors_PasswordEncode corsPasswordEncode) {
        this.loadUserDetails = loadUserDetails;
        this.corsPasswordEncode = corsPasswordEncode;
    }

    @Value("${app.security.pepper}")
    private String salt;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = loadUserDetails.loadUserByUsername(username);
        if(corsPasswordEncode.passwordEncoder().matches(password+salt, userDetails.getPassword())){
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
