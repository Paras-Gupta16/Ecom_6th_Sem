package com.example.Ecom_Seller.utility;

import com.example.Ecom_Seller.entity.SellerEntity;
import com.example.Ecom_Seller.repository.MongoSeller_Repo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoadUserDetails implements UserDetailsService {

    private final MongoSeller_Repo mongoSellerRepo;

    public LoadUserDetails(MongoSeller_Repo mongoSellerRepo) {
        this.mongoSellerRepo = mongoSellerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SellerEntity sellerEntity = mongoSellerRepo.findBySellerEmail(username);
        if(sellerEntity==null){
            throw new UsernameNotFoundException("Invalid Credentials");
        }
        return User.builder()
                   .username(sellerEntity.getSellerEmail())
                   .password(sellerEntity.getSellerPassword())
                   .authorities(sellerEntity.getRole()).build();
    }
}
