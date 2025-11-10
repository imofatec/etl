package com.imo.etl_imo.model.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "lessons")
public class LessonPojo {

    @Id
    private ObjectId id;

    @Field("title")
    private String title;

    @Field("courseId")
    private ObjectId courseId;

    @Field("order")
    private Integer order;

    @Field("duration")
    private Integer duration;
}
