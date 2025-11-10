package com.imo.etl_imo.processor;

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
            case "none", "nenhum" -> 0;                                         // Sem grau acadêmico
            case "technical", "técnico" -> 1;                                   // Técnico
            case "associate", "tecnólogo" -> 2;                                 // Tecnólogo/Associate
            case "bachelor", "graduação", "bachelor's", "bacharelado" -> 3;     // Graduação
            case "licentiate", "licenciatura" -> 3;                             // Licenciatura (mesmo nível que Bachelor)
            case "mba" -> 4;                                                    // MBA
            case "master", "mestrado", "master's" -> 5;                         // Mestrado
            case "doctoral", "phd", "doutorado", "doctorate" -> 6;              // Doutorado
            case "postdoc", "pós-doutorado", "post-doctoral" -> 7;              // Pós-doutorado
            default -> 3;  // Default para graduação
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
