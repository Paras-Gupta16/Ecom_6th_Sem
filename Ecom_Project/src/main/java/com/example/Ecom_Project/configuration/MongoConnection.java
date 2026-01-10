package com.example.Ecom_Project.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConnection {

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb://127.0.0.1:27017/ecom_project");
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoClient(),"ecom_database");
    }
}
