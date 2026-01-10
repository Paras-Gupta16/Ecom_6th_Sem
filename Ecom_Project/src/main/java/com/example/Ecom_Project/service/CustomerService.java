package com.example.Ecom_Project.service;

import java.time.LocalDateTime;

import com.example.Ecom_Project.entity.Customer;
import com.example.Ecom_Project.repository.CustomerRepository;
import com.example.Ecom_Project.security.CustomerSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerSecurity customerSecurity;
    public CustomerService(CustomerRepository customerRepository, CustomerSecurity customerSecurity) {
        this.customerRepository = customerRepository;
        this.customerSecurity = customerSecurity;
    }

    @Value("${app.security.pepper}")
    private String salt;

    //Save Customer Details
    public String saveCustomer(Customer customer){
        try{
            customer.setDateCreated(LocalDateTime.now());
            String hashPwd = customerSecurity
                    .passwordEncoder()
                    .encode(customer.getCustomerPassword()) + salt  ;
            customer.setCustomerPassword(hashPwd);
            customerRepository.save(customer);
            return "Data Save";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    // Fetch Customer Details using email
    public Customer returnCustomerData(String customerEmail){
        try{
            return customerRepository.findByCustomerEmail(customerEmail);
        }catch (Exception e){
            return null;
        }
    }

    public String deleteCustomerDetails(String customerEmail){
        try{
            customerRepository.deleteByCustomerEmail(customerEmail);
            return "Customer with Email:"+customerEmail+"deleted";
        }catch (Exception e){
            return e.getMessage();
        }
    }


}
