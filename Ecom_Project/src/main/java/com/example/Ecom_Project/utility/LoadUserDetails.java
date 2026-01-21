package com.example.Ecom_Project.utility;

import com.example.Ecom_Project.entity.Customer;
import com.example.Ecom_Project.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoadUserDetails implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public LoadUserDetails(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCustomerEmail(username);
        if(customer==null){
            throw new RuntimeException("Username not found");
        }
        return User.builder()
                   .username(customer.getCustomerEmail())
                   .password(customer.getCustomerPassword())
                   .authorities(customer.getRole())
                   .build();
    }
}
