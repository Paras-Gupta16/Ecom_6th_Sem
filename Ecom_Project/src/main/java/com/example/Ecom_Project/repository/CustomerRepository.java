package com.example.Ecom_Project.repository;

import com.example.Ecom_Project.entity.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
    Customer findByCustomerEmail(String customerEmail);
    Customer deleteByCustomerEmail(String customerEmail);
}
