package com.imo.etl_imo.csv.service.impl;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import com.imo.etl_imo.csv.config.CsvConfig;
import com.imo.etl_imo.csv.service.ReturnAnalyticsCsvService;
import com.imo.etl_imo.model.dto.AnalyticsDetails;
import com.imo.etl_imo.repository.AnalyticsRepository;

@Service
public class ReturnAnalyticsCsvServiceImpl implements ReturnAnalyticsCsvService{

    private final AnalyticsRepository analyticsRepository;
    private final CsvConfig csvConfig;
    
    public ReturnAnalyticsCsvServiceImpl(AnalyticsRepository analyticsRepository,
                                         CsvConfig csvConfig) {
        this.analyticsRepository = analyticsRepository;
        this.csvConfig = csvConfig;
    }


    @Override
    public byte[] execute(){

        var data = analyticsRepository.findAll()
                    .stream()
                    .map(d -> new AnalyticsDetails(
                        d.getCourseCategory(),
                        d.getCourseLevel(),
                        d.getCourseLessonsCount(),
                        d.getUserAcademicDegree(),
                        d.getUserExperienceLevel(),
                        d.getUserAge(),
                        d.getUserInterestCategory1(),
                        d.getUserInterestCategory2(),
                        d.getUserAvailableTime(),
                        d.getLessonsWatchedCount(),
                        d.getCompletionRate(),
                        d.getCompletionProbability()
                    )).toList();
        
        String csvStream = csvConfig.generateCsv(data);
        var csvBytes = csvStream.getBytes(StandardCharsets.UTF_8);
        return csvBytes;

    }
    
}
