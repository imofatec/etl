package com.imo.etl_imo.writer;

import com.imo.etl_imo.model.dto.AnalyticsDetails;
import com.imo.etl_imo.model.entity.Analytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class AnalyticsWriter implements ItemWriter<AnalyticsDetails> {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsWriter.class);
    private final MongoTemplate outputMongoTemplate;

    public AnalyticsWriter(@Qualifier("outputMongoTemplate") MongoTemplate outputMongoTemplate) {
        this.outputMongoTemplate = outputMongoTemplate;
    }

    @Override
    public void write(Chunk<? extends AnalyticsDetails> chunk) throws Exception {
        if (chunk.isEmpty()) {
            return;
        }

        List<Analytics> analyticsRecords = chunk.getItems().stream()
            .map(item -> convertToAnalytics(item))
            .toList();

        outputMongoTemplate.insertAll(analyticsRecords);
        logger.info("{} registros inseridos", analyticsRecords.size());
    }

    private Analytics convertToAnalytics(AnalyticsDetails details) {
        return Analytics.builder()
            .courseCategory(details.getCourseCategory())
            .courseLevel(details.getCourseLevel())
            .courseLessonsCount(details.getCourseLessonsCount())
            .userAcademicDegree(details.getUserAcademicDegree())
            .userExperienceLevel(details.getUserExperienceLevel())
            .userAge(details.getUserAge())
            .userInterestCategory1(details.getUserInterestCategory1())
            .userInterestCategory2(details.getUserInterestCategory2())
            .userAvailableTime(details.getUserAvailableTime())
            .lessonsWatchedCount(details.getLessonsWatchedCount())
            .completionRate(details.getCompletionRate())
            .completionProbability(details.getCompletionProbability())
            .build();
    }
}
