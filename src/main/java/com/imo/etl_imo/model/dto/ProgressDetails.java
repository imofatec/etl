package com.imo.etl_imo.model.dto;

import com.imo.etl_imo.model.pojo.CoursePojo;
import com.imo.etl_imo.model.pojo.ProgressPojo;
import com.imo.etl_imo.model.pojo.UserPojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

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
    private Double completionRate;
    private Double completionProbability;

    public static ProgressDetails from(ProgressPojo progress, UserPojo user, CoursePojo course) {
        if (progress == null || user == null || course == null) {
            throw new IllegalArgumentException("Progress, User e Course não podem ser null");
        }

        int watchedCount = progress.getLessonsWatchedCount();
        int totalLessons = course.getLessonsCount() != null ? course.getLessonsCount() : 0;
        double completionRate = totalLessons > 0 ? (double) watchedCount / totalLessons : 0.0;

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

    public String getFormattedCompletionRate() {
        return completionRate != null 
            ? String.format("%.2f%%", completionRate * 100)
            : "0.00%";
    }

    public boolean isCompleted() {
        return completionRate != null && completionRate >= 1.0;
    }

    public String getFormattedCompletionProbability() {
        return completionProbability != null 
            ? String.format("%.4f (%.2f%%)", completionProbability, completionProbability * 100)
            : "N/A";
    }
}
