package com.example.Ecom_Seller.repository;

import com.example.Ecom_Seller.entity.SellerEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoSeller_Repo extends MongoRepository<SellerEntity, ObjectId> {

    SellerEntity findBySellerEmail(String sellerEmail);
    SellerEntity deleteBySellerEmail(String sellerEmail);
}
