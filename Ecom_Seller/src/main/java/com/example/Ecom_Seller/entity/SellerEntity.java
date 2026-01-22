package com.example.Ecom_Seller.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seller")
@Getter
@Setter
public class SellerEntity {

    @Id
    private ObjectId sellerId;

    private String sellerName;

    private String sellerEmail;

    private List<String> sellerAddress;

    private String role;

    private String sellerPassword;

    private KycVerification kycVerification;

    private List<String> pickupAddresses;

    private LocalDateTime dateCreated;
}
