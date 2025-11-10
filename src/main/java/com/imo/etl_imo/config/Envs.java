package com.imo.etl_imo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Envs {

    @Value("${source.mongodb.uri}")
    public String SOURCE_MONGODB_URI;

    @Value("${spring.data.mongodb.uri}")
    public String SPRING_MONGODB_URI;
}
