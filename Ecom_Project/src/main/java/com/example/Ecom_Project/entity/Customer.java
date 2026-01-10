package com.example.Ecom_Project.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Getter
@Setter
public class Customer {
    @Id
    private ObjectId customerId;

    @Indexed(unique = true)
    private String customerEmail;
    private String customerPassword;
    private List<String> addresses;
    private String contactNumber;
    private String gender;
    private LocalDateTime dateCreated;
}
