package com.imo.etl_imo.model.dto;

import com.imo.etl_imo.model.pojo.CoursePojo;
import com.imo.etl_imo.model.pojo.ProgressPojo;
import com.imo.etl_imo.model.pojo.UserPojo;
import com.imo.etl_imo.processor.utils.NumberFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    private ObjectId progressId;
    private ObjectId userId;
    private ObjectId courseId;
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

    public static ProgressDetails from(ProgressPojo progress, UserPojo user, CoursePojo course) {
        if (progress == null || user == null || course == null) {
            throw new IllegalArgumentException("Progress, User e Course não podem ser null");
        }

        int watchedCount = progress.getLessonsWatchedCount();
        int totalLessons = course.getLessonsCount() != null ? course.getLessonsCount() : 0;
        BigDecimal completionRate = totalLessons > 0 
            ? NumberFormatter.formatNumberToBigDecimal((double) watchedCount / totalLessons, 3)
            : BigDecimal.ZERO;

        return ProgressDetails.builder()
            .progressId(progress.getId())
            .userId(user.getId())
            .courseId(course.getId())
            .courseCategory(course.getCategoryName())
            .courseLevel(course.getLevelName())
            .courseLessonsCount(course.getLessonsCount())
            .userAcademicDegree(user.getAcademicDegree())
            .userExperienceLevel(user.getExperienceLevel())
            .userAge(user.getAge())
            .userInterestCategory1(user.getFirstInterestCategory())
            .userInterestCategory2(user.getSecondInterestCategory())
            .userAvailableTime(user.getAvailableTimePerDay())
            .lessonsWatchedCount(watchedCount)
            .completionRate(completionRate)
            .build();
    }

    public String getProgressIdAsString() {
        return progressId != null ? progressId.toHexString() : null;
    }

    public String getUserIdAsString() {
        return userId != null ? userId.toHexString() : null;
    }

    public String getCourseIdAsString() {
        return courseId != null ? courseId.toHexString() : null;
    }
}
