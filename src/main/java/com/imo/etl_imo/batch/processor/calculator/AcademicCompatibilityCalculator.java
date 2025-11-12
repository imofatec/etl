package com.imo.etl_imo.batch.processor.calculator;

import com.imo.etl_imo.model.dto.ProgressDetails;
import org.springframework.stereotype.Component;

@Component
public class AcademicCompatibilityCalculator {

    private static final double DEFAULT_SCORE = 50.0;

    public double calculate(ProgressDetails details) {
        String userDegree = details.getUserAcademicDegree();
        String courseLevel = details.getCourseLevel();

        if (userDegree == null || courseLevel == null) {
            return DEFAULT_SCORE;
        }

        int userAcademicScore = mapAcademicDegree(userDegree.toLowerCase());
        int courseLevelScore = mapCourseLevel(courseLevel.toLowerCase());
        int difference = Math.abs(userAcademicScore - courseLevelScore);

        return switch (difference) {
            case 0 -> 85.0;    
            case 1 -> 75.0;     
            case 2 -> 60.0;   
            case 3 -> 45.0;     
            default -> 35.0;    
        };
    }

    private int mapAcademicDegree(String degree) {
        return switch (degree) {
            case "none", "nenhum" -> 0;                                   
            case "technical", "técnico" -> 1;                                
            case "bachelor", "graduação", "bachelor's", "bacharelado" -> 3;
            case "licentiate", "licenciatura" -> 3;                           
            case "mba" -> 4;                                                 
            case "master", "mestrado", "master's" -> 5;                    
            case "doctoral", "phd", "doutorado", "doctorate" -> 6;         
            case "postdoc", "pós-doutorado", "post-doctoral" -> 7;            
            default -> 3; 
        };
    }

    private int mapCourseLevel(String level) {
        return switch (level) {
            case "beginner", "iniciante", "básico" -> 1;
            case "intermediate", "intermediário" -> 2;
            case "advanced", "avançado" -> 3;
            case "expert", "especialista" -> 4;
            case "professional", "profissional" -> 5;
            default -> 2;
        };
    }
}
