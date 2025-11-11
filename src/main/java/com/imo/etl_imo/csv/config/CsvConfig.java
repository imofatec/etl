package com.imo.etl_imo.csv.config;

import java.util.List;

import org.springframework.stereotype.Component;

import com.imo.etl_imo.model.dto.AnalyticsDetails;

@Component
public class CsvConfig {
    private static final String CSV_HEADER = "courseCategory,courseLevel,courseLessonsCount,userAcademicDegree, userExperienceLevel, userAge, userInterestCategory1, userInterestCategory2, userAvailableTime, lessonsWatchedCount, completionRate, completionProbability";

    public String generateCsv(List<AnalyticsDetails> analyticsDetails) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER).append("\n");

        for (AnalyticsDetails analytics : analyticsDetails) {
            csvContent
                .append(analytics.getCourseCategory()).append(",")
                .append(analytics.getCourseLevel()).append(",")
                .append(analytics.getCourseLessonsCount()).append(",")
                .append(analytics.getUserAcademicDegree()).append(",")
                .append(analytics.getUserExperienceLevel()).append(",")
                .append(analytics.getUserAge()).append(",")
                .append(analytics.getUserInterestCategory1()).append(",")
                .append(analytics.getUserInterestCategory2()).append(",")
                .append(analytics.getUserAvailableTime()).append(",")
                .append(analytics.getLessonsWatchedCount()).append(",")
                .append(analytics.getCompletionRate()).append(",")
                .append(analytics.getCompletionProbability())
                .append("\n");
        }

        return csvContent.toString();
    }
}