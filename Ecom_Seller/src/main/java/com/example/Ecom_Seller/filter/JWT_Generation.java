package com.example.Ecom_Seller.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.example.Ecom_Seller.utility.ApplicationConstants;
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

public class JWT_Generation extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            Environment env = getEnvironment();
            if(env!=null){
                String key = env.getProperty(ApplicationConstants.JWT_SECRET_KEY
                        ,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
                String jwtBuilder = Jwts
                        .builder()
                        .claim("username",authentication.getName())
                        .claim("role",authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime()+300000000))
                        .signWith(secretKey)
                        .compact();
                response.setHeader(ApplicationConstants.JWT_HEADER,jwtBuilder);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/seller/generate/jwt");
    }
}
