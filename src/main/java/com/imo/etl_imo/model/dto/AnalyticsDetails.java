package com.imo.etl_imo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String courseCategory;
    private String courseLevel;
    private Integer courseLessonsCount;
    private String userAcademicDegree;
    private String userExperienceLevel;
    private Integer userAge;
    private String userInterestCategory1;
    private String userInterestCategory2;
    private String userAvailableTime;
    private Integer lessonsWatchedCount;
    private BigDecimal completionRate;
    private BigDecimal completionProbability;

    public static AnalyticsDetails from(ProgressDetails progressDetails) {
        if (progressDetails == null) {
            throw new IllegalArgumentException("ProgressDetails não pode ser null");
        }

        return AnalyticsDetails.builder()
            .courseCategory(progressDetails.getCourseCategory())
            .courseLevel(progressDetails.getCourseLevel())
            .courseLessonsCount(progressDetails.getCourseLessonsCount())
            .userAcademicDegree(progressDetails.getUserAcademicDegree())
            .userExperienceLevel(progressDetails.getUserExperienceLevel())
            .userAge(progressDetails.getUserAge())
            .userInterestCategory1(progressDetails.getUserInterestCategory1())
            .userInterestCategory2(progressDetails.getUserInterestCategory2())
            .userAvailableTime(progressDetails.getUserAvailableTime())
            .lessonsWatchedCount(progressDetails.getLessonsWatchedCount())
            .completionRate(progressDetails.getCompletionRate())
            .completionProbability(progressDetails.getCompletionProbability())
            .build();
    }
}
