package com.imo.etl_imo.model.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;
import java.util.List;

@Data
public class ProgressWithDetails {
    
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
    
    @Field("user")
    private List<UserPojo> user;
    
    @Field("course")
    private List<CoursePojo> course;
    
    public UserPojo getUserData() {
        return user != null && !user.isEmpty() ? user.get(0) : null;
    }
    
    public CoursePojo getCourseData() {
        return course != null && !course.isEmpty() ? course.get(0) : null;
    }
    
    public int getLessonsWatchedCount() {
        return lessonsWatched != null ? lessonsWatched.size() : 0;
    }
}
