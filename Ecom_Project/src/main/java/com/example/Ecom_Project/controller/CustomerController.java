package com.example.Ecom_Project.controller;

import com.example.Ecom_Project.entity.Customer;
import com.example.Ecom_Project.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/health")
    public String seeHealth(){
        return "Health is Ok";
    }

    @PostMapping("/save/details")
    public ResponseEntity<?> saveCustomerDetails(@RequestBody Customer customer){
        try{
            return new ResponseEntity<>(customerService.saveCustomer(customer),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDetails/email")
    public ResponseEntity<?> getCustomerDetailsByEmail(@RequestParam String customerEmail){
        try{
            if(customerEmail.isEmpty()){
                return new ResponseEntity<>("Email Can't be empty",HttpStatus.NO_CONTENT);
            }
            if(customerService.returnCustomerData(customerEmail)==null){
                return new ResponseEntity<>("Data Not found",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customerService.returnCustomerData(customerEmail),HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/email")
    public ResponseEntity<?> deleteCustomerDetails(@RequestParam String customerEmail){
        try{
            if(customerEmail.isEmpty()){
                return new ResponseEntity<>("Please Enter the email",HttpStatus.BAD_REQUEST);
            }
            String data = customerService.deleteCustomerDetails(customerEmail);
            if(data==null){
                return new ResponseEntity<>("User not exists",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
