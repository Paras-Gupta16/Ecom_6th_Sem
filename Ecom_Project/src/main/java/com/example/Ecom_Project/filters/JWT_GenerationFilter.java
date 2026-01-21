package com.example.Ecom_Project.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.example.Ecom_Project.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWT_GenerationFilter extends OncePerRequestFilter {


    // this code generate JWT Token for login and authentication part

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            Environment environment = getEnvironment();
            if(environment!=null) {
                String key = environment
                        .getProperty(ApplicationConstants.JWT_SECRET_KEY
                                , ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secret = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
                String jwtBuilder = Jwts.builder()
                                        .subject("JWT_TOKEN")
                                        .claim("username", authentication.getName())
                                        .claim("authorities", authentication
                                                .getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.joining("")))
                                        .issuedAt(new Date())
                                        .expiration(new Date(new Date().getTime() + 300000000))
                                        .signWith(secret)
                                        .compact();
                response.setHeader(ApplicationConstants.JWT_HEADER, jwtBuilder);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/jwt/token/generation");
    }
}
