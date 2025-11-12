package com.imo.etl_imo.model.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
public class UserPojo {

    private ObjectId id;

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("academicDegree")
    private String academicDegree;

    @Field("experienceLevel")
    private String experienceLevel;

    @Field("birthDate")
    private Date birthDate;

    @Field("categoriesOfInterest")
    private List<String> categoriesOfInterest;

    @Field("availableTimePerDay")
    private String availableTimePerDay;

    public Integer getAge() {
        if (birthDate == null) {
            return null;
        }
        
        LocalDate birthLocalDate = birthDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        
        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }

    public String getFirstInterestCategory() {
        return categoriesOfInterest != null && !categoriesOfInterest.isEmpty()
            ? categoriesOfInterest.get(0)
            : null;
    }

    public String getSecondInterestCategory() {
        return categoriesOfInterest != null && categoriesOfInterest.size() > 1
            ? categoriesOfInterest.get(1)
            : null;
    }
}
