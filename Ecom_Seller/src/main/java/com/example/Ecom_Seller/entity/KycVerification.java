package com.example.Ecom_Seller.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KycVerification {

    private String gst;
    private BankDetails bankDetails;
    private GovtIdDetails govtIdDetails;
}

