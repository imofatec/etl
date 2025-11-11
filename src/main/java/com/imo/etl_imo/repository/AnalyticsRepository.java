package com.imo.etl_imo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.imo.etl_imo.model.entity.Analytics;

@Repository
public interface AnalyticsRepository extends MongoRepository<Analytics, String> {

    List<Analytics> findAll();
    
}