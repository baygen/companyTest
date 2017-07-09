/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.main.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.test.managers.CompanysManager;
import com.test.managers.ManagerCRUD;
import com.test.managers.dao.BasicDAO;
import com.test.managers.dao.DAOManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Configuration
public class AppConfig {
    
//    TODO @Bean mogoclienturi
    
    
    @Bean
    public MongoTemplate mongoTemplate(){
        String uri = "mongodb://oleg:root@ds153422.mlab.com:53422/testcompany";
        return new MongoTemplate(new MongoClient(new MongoClientURI(uri)), "maincompany");
    }
    
    @Bean 
    public BasicDAO dao(){
        return new DAOManager(mongoTemplate());
    }
    
    @Bean
    public ManagerCRUD manager(){
        return new CompanysManager(dao());
    } 
    
}