package com.example.Ecom_Project.controller;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.example.Ecom_Project.constants.ApplicationConstants;
import com.example.Ecom_Project.entity.JWT_Request;
import com.example.Ecom_Project.entity.JWT_Respond;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/jwt/token")
public class JWT_GenerationController {

    private AuthenticationManager authenticationManager;
    private Environment env;

    @PostMapping("/generation")
    public ResponseEntity<?> jwtTokenGeneration(@RequestBody JWT_Request jwtRequest){
        if(jwtRequest.customerEmail().isEmpty()||jwtRequest.customerPassword().isEmpty()){
            return new ResponseEntity<>("Email or password can't be empty", HttpStatus.BAD_REQUEST);
        }
        Authentication authenticationUser = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.customerEmail(),jwtRequest.customerPassword()));
        String jwt = "";
        if(null!=env){
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey keys = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            if(keys!=null){
                jwt = Jwts.builder()
                          .issuer("Paras")
                          .subject("JWT_Token")
                          .claim("username",jwtRequest.customerEmail())
                          .claim("authority",authenticationUser
                                  .getAuthorities()
                                  .stream()
                                  .map(GrantedAuthority::getAuthority)
                                  .collect(Collectors.joining("")))
                          .signWith(keys).compact();
            }
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("JWT_Token",jwt)
                .body(new JWT_Respond(HttpStatus.CREATED.getReasonPhrase(),jwt));
    }
}
