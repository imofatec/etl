package com.imo.etl_imo.processor;

import com.imo.etl_imo.model.dto.ProgressDetails;
import org.springframework.stereotype.Component;

@Component
public class InterestCompatibilityCalculator {

    private static final double DEFAULT_SCORE = 50.0;
    private static final double MATCH_PRIMARY = 90.0;    
    private static final double MATCH_SECONDARY = 75.0;   
    private static final double NO_MATCH = 40.0;        

    public double calculate(ProgressDetails details) {
        String courseCategory = details.getCourseCategory();
        String userInterest1 = details.getUserInterestCategory1();
        String userInterest2 = details.getUserInterestCategory2();

        if (courseCategory == null || (userInterest1 == null && userInterest2 == null)) {
            return DEFAULT_SCORE;
        }

        String normalizedCourseCategory = normalize(courseCategory);
        String normalizedInterest1 = normalize(userInterest1);
        String normalizedInterest2 = normalize(userInterest2);

        if (normalizedCourseCategory.equals(normalizedInterest1)) {
            return MATCH_PRIMARY;
        }

        if (normalizedCourseCategory.equals(normalizedInterest2)) {
            return MATCH_SECONDARY;
        }

        if (isPartialMatch(normalizedCourseCategory, normalizedInterest1) ||
            isPartialMatch(normalizedCourseCategory, normalizedInterest2)) {
            return 65.0; 
        }

        return NO_MATCH;
    }

    private String normalize(String category) {
        if (category == null) {
            return "";
        }
        return category.toLowerCase()
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("[^a-z0-9\\s]", "");
    }

    private boolean isPartialMatch(String category1, String category2) {
        if (category1 == null || category2 == null || category1.isEmpty() || category2.isEmpty()) {
            return false;
        }

        return category1.contains(category2) || category2.contains(category1);
    }
}
