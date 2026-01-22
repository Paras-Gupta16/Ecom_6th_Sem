package com.example.Ecom_Seller.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Ecom_Seller.entity.SellerEntity;
import com.example.Ecom_Seller.repository.MongoSeller_Repo;
import org.springframework.stereotype.Service;

@Service
public class MongoSeller_Service {

    private final MongoSeller_Repo mongoSellerRepo;

    public MongoSeller_Service(MongoSeller_Repo mongoSellerRepo) {
        this.mongoSellerRepo = mongoSellerRepo;
    }

    public String saveSeller(SellerEntity sellerEntity){
        try{
            if(mongoSellerRepo.findBySellerEmail(sellerEntity.getSellerEmail())!=null){
                return "Email Id Already Exists";
            }
            sellerEntity.setDateCreated(LocalDateTime.now());
            mongoSellerRepo.save(sellerEntity);
            return "Seller Details Saved";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public SellerEntity seeSellerDetails(String sellerEmail)throws Exception{
        try{
            return mongoSellerRepo.findBySellerEmail(sellerEmail);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String deleteSeller(String sellerEmail){
        try{
            mongoSellerRepo.deleteBySellerEmail(sellerEmail);
            return "Customer with Email:"+sellerEmail+" deleted";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateSellerEmailId(String sellerEmail, String sellerNewEmailId) throws Exception{
        try{
            if(sellerEmail==null||sellerNewEmailId==null){
                throw new Exception("Data can't be null");
            }
            SellerEntity entity = mongoSellerRepo.findBySellerEmail(sellerEmail);
            entity.setSellerEmail(sellerNewEmailId);
            mongoSellerRepo.save(entity);
            return "Email Updated";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public  String updateSellerAddress(String sellerEmailId,List<String>newAddress){
        try{
            if(newAddress==null||sellerEmailId==null){
                return "Address can't be null or EmailId can't be null";
            }
            SellerEntity seller = mongoSellerRepo.findBySellerEmail(sellerEmailId);
            seller.setSellerAddress(newAddress);
            mongoSellerRepo.save(seller);
            return "your address update successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
