package com.imo.etl_imo.model.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;
import java.util.List;


@Data
@Document(collection = "progress")
public class ProgressPojo {

    @Id
    private ObjectId id;

    @Field("userId")
    private ObjectId userId;

    @Field("courseId")
    private ObjectId courseId;

    @Field("lessonsWatched")
    private List<ObjectId> lessonsWatched;

    @Field("createdAt")
    private Date createdAt;

    @Field("updatedAt")
    private Date updatedAt;

    public int getLessonsWatchedCount() {
        return lessonsWatched != null ? lessonsWatched.size() : 0;
    }
}
