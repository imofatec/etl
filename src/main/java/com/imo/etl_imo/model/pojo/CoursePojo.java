package com.imo.etl_imo.model.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "courses")
public class CoursePojo {

    @Id
    private ObjectId id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("category.name")
    private String categoryName;

    @Field("level.name")
    private String levelName;

    @Field("lessonsCount")
    private Integer lessonsCount;
}
