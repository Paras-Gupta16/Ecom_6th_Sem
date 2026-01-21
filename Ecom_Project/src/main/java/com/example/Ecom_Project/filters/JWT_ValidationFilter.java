package com.example.Ecom_Project.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import com.example.Ecom_Project.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWT_ValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(ApplicationConstants.JWT_HEADER);
        try{
            if(header!=null){
                Environment env = getEnvironment();
                if(env!=null){
                    String key = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
                    if(secretKey!=null){
                        Claims claims = Jwts.parser()
                                            .verifyWith(secretKey)
                                            .build()
                                            .parseSignedClaims(header)
                                            .getPayload();
                        String username = String.valueOf(claims.get("username"));
                        String auth = String.valueOf(claims.get("authority"));
                        Authentication authenticationResponse = new UsernamePasswordAuthenticationToken(username
                                ,null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(auth));
                        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
                    }
                }
            }
        }catch (Exception e){
            throw new IOException(e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/jwt/token/generation");
    }
}
