package com.imo.etl_imo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.imo.etl_imo.csv.service.impl.ReturnAnalyticsCsvServiceImpl;



@RestController
public class GenerateCsvAnalyticsController extends BaseController{
    
    private final ReturnAnalyticsCsvServiceImpl returnAnalyticsCsvServiceImpl;

    public GenerateCsvAnalyticsController(ReturnAnalyticsCsvServiceImpl returnAnalyticsCsvServiceImpl) {
        this.returnAnalyticsCsvServiceImpl = returnAnalyticsCsvServiceImpl;
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> exportCsv(){

        byte[] csvBytes = returnAnalyticsCsvServiceImpl.execute();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv;charset=UTF-8")); 
        headers.setContentDispositionFormData("attachment", "analytics.csv");
        headers.setContentLength(csvBytes.length);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
