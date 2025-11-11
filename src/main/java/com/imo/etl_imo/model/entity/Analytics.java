package com.imo.etl_imo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "analytics")
public class Analytics {

    @Id
    private String id;

    @Field("course_category")
    private String courseCategory;

    @Field("course_level")
    private String courseLevel;

    @Field("course_lessons_count")
    private Integer courseLessonsCount;

    @Field("user_academic_degree")
    private String userAcademicDegree;

    @Field("user_experience_level")
    private String userExperienceLevel;

    @Field("user_age")
    private Integer userAge;

    @Field("user_interest_category_1")
    private String userInterestCategory1;

    @Field("user_interest_category_2")
    private String userInterestCategory2;

    @Field("user_available_time")
    private String userAvailableTime;

    @Field("lessons_watched_count")
    private Integer lessonsWatchedCount;

    @Field("completion_rate")
    private BigDecimal completionRate;

    @Field("completion_probability")
    private BigDecimal completionProbability;
}
