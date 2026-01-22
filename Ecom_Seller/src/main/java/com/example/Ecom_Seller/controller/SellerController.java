package com.example.Ecom_Seller.controller;

import java.util.List;

import com.example.Ecom_Seller.entity.SellerEntity;
import com.example.Ecom_Seller.service.MongoSeller_Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final MongoSeller_Service mongoSellerService;

    public SellerController(MongoSeller_Service mongoSellerService) {
        this.mongoSellerService = mongoSellerService;
    }

    @PostMapping("/save/details")
    public ResponseEntity<?> saveSeller(@RequestBody SellerEntity sellerEntity){
        try{
            if(sellerEntity!=null){
                return new ResponseEntity<>(mongoSellerService.saveSeller(sellerEntity),HttpStatus.OK);
            }
            return new ResponseEntity<>("Data can't be null",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/see/seller")
    public ResponseEntity<?> seeSellerDetails(@RequestParam String sellerEmail){
        try{
            if(sellerEmail!=null&&!sellerEmail.isEmpty()){
                return new ResponseEntity<>(mongoSellerService.seeSellerDetails(sellerEmail),HttpStatus.OK);
            }
            return new ResponseEntity<>("Email can't be empty",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/seller/delete")
    public ResponseEntity<?> deleteSeller(@RequestParam String sellerEmail){
        try{
            if(sellerEmail==null||sellerEmail.isEmpty()){
                return new ResponseEntity<>("Email can't be empty",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(mongoSellerService.deleteSeller(sellerEmail),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/seller/email")
    public ResponseEntity<?> updateSeller(@RequestParam String sellerEmail, @RequestBody String newSellerEmail){
        try{
            if(sellerEmail==null||newSellerEmail==null){
                return new ResponseEntity<>("Data cant be null",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(mongoSellerService.updateSellerEmailId(sellerEmail,newSellerEmail),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/seller/address")
    public ResponseEntity<?> updateAddress(@RequestParam String sellerEmail, @RequestBody List<String>newAddress){
        try{
            if(sellerEmail==null||newAddress==null){
                return new ResponseEntity<>("Data can't be null",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(mongoSellerService.updateSellerAddress(sellerEmail, newAddress),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
