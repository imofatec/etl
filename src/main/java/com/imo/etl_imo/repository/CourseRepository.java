package com.imo.etl_imo.repository;

import com.imo.etl_imo.model.pojo.CoursePojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<CoursePojo, ObjectId> {
}
