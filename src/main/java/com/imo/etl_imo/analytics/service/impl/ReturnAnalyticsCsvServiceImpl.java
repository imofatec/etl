package com.imo.etl_imo.analytics.service.impl;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import com.imo.etl_imo.analytics.util.CsvGenerator;
import com.imo.etl_imo.analytics.service.ReturnAnalyticsCsvService;
import com.imo.etl_imo.model.dto.AnalyticsDetails;
import com.imo.etl_imo.repository.AnalyticsRepository;

@Service
public class ReturnAnalyticsCsvServiceImpl implements ReturnAnalyticsCsvService{

    private final AnalyticsRepository analyticsRepository;
    private final CsvGenerator csvGenerator;
    
    public ReturnAnalyticsCsvServiceImpl(AnalyticsRepository analyticsRepository,
                                         CsvGenerator csvGenerator) {
        this.analyticsRepository = analyticsRepository;
        this.csvGenerator = csvGenerator;
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
        
        String csvStream = csvGenerator.generateCsv(data);
        var csvBytes = csvStream.getBytes(StandardCharsets.UTF_8);
        return csvBytes;

    }
    
}
