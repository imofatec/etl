package com.imo.etl_imo.batch.processor.calculator;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.util.NumberFormatter;
import org.springframework.stereotype.Component;

@Component
public class CompletionScoreCalculator {

    private static final double WEIGHT_COMPLETION_PROGRESS = 0.35;   
    private static final double WEIGHT_ACADEMIC_COMPATIBILITY = 0.20;
    private static final double WEIGHT_INTEREST_COMPATIBILITY = 0.20;
    private static final double WEIGHT_EXPERIENCE_ENGAGEMENT = 0.15; 
    private static final double WEIGHT_TIME_AVAILABILITY = 0.10;     
    private static final double DEFAULT_CATEGORY_SCORE = 50.0; 

    private final AcademicCompatibilityCalculator academicCalculator;
    private final InterestCompatibilityCalculator interestCalculator;

    public CompletionScoreCalculator(
            AcademicCompatibilityCalculator academicCalculator,
            InterestCompatibilityCalculator interestCalculator) {
        this.academicCalculator = academicCalculator;
        this.interestCalculator = interestCalculator;
    }

    public double calculateScore(ProgressDetails details) {
            double completionScore = calculateCompletionProgressScore(details);
            double academicScore = academicCalculator.calculate(details);
            double interestScore = interestCalculator.calculate(details);
            double experienceScore = calculateExperienceEngagementScore(details);
            double timeAvailabilityScore = calculateTimeAvailabilityScore(details);

            double finalScore =
                (completionScore * WEIGHT_COMPLETION_PROGRESS) +
                (academicScore * WEIGHT_ACADEMIC_COMPATIBILITY) +
                (interestScore * WEIGHT_INTEREST_COMPATIBILITY) +
                (experienceScore * WEIGHT_EXPERIENCE_ENGAGEMENT) +
                (timeAvailabilityScore * WEIGHT_TIME_AVAILABILITY);

            finalScore = Math.max(0, Math.min(100, finalScore));

            return finalScore;

    }

    private double calculateCompletionProgressScore(ProgressDetails details) {
        int lessonsWatched = details.getLessonsWatchedCount();
        int totalLessons = details.getCourseLessonsCount();

        if (totalLessons <= 0) {
            return DEFAULT_CATEGORY_SCORE;
        }

        double completionRate = (double) lessonsWatched / totalLessons;
        return NumberFormatter.formatNumber(completionRate * 100, 3);
    }

    private double calculateExperienceEngagementScore(ProgressDetails details) {
        String experienceLevel = details.getUserExperienceLevel();

        return switch (experienceLevel != null ? experienceLevel.toLowerCase() : "unknown") {
            case "beginner" -> 45.0;  
            case "intermediate" -> 65.0;  
            case "advanced" -> 80.0;    
            case "expert" -> 85.0;        
            default -> DEFAULT_CATEGORY_SCORE;
        };
    }

    private double calculateTimeAvailabilityScore(ProgressDetails details) {
        String availableTime = details.getUserAvailableTime();
        int courseLessonsCount = details.getCourseLessonsCount();

        double baseScore = switch (availableTime != null ? availableTime.toLowerCase() : "unknown") {
            case "less_than_one_hour" -> 35.0;     
            case "one_to_two_hours" -> 55.0;      
            case "two_to_four_hours" -> 75.0;   
            case "more_than_four_hours" -> 90.0;    
            default -> DEFAULT_CATEGORY_SCORE;
        };

       
        if (courseLessonsCount > 50) {
            baseScore *= 0.9; 
        } else if (courseLessonsCount < 10) {
            baseScore = Math.min(100, baseScore + 10);  
        }

        return baseScore;
    }
}
