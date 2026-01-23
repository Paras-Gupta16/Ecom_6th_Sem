package com.example.Ecom_Seller.controller;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.example.Ecom_Seller.entity.JWT_Request;
import com.example.Ecom_Seller.entity.JWT_Response;
import com.example.Ecom_Seller.utility.ApplicationConstants;
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
@RequestMapping("/seller/generate/jwt")
public class JWT_SellerController {

    private AuthenticationManager authenticationManager;
    private Environment env;

    @PostMapping("/generate/jwt")
    public ResponseEntity<?> generateToken(@RequestBody JWT_Request jwtRequest){
        try{
            if(jwtRequest.sellerEmail()==null||jwtRequest.sellerPassword()==null){
                return new ResponseEntity<>("Data can't be null", HttpStatus.NO_CONTENT);
            }
            String jwt = "";
            Authentication authenticationUser = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.sellerEmail(),jwtRequest.sellerPassword()));
            if(null!=env){
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                if(key!=null){
                    jwt = Jwts
                            .builder()
                            .issuer("Paras")
                            .claim("username",jwtRequest.sellerEmail())
                            .claim("authority",authenticationUser
                                    .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("")))
                            .signWith(key)
                            .compact();
                }
            }
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("JWT_Token",ApplicationConstants.JWT_HEADER)
                    .body(new JWT_Response(HttpStatus.CREATED.getReasonPhrase(), jwt));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
}
