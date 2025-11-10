package com.imo.etl_imo.repository;

import com.imo.etl_imo.model.pojo.ProgressPojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends MongoRepository<ProgressPojo, ObjectId> {

    List<ProgressPojo> findAllByOrderByCreatedAtDesc();

    List<ProgressPojo> findByUserId(ObjectId userId);

    List<ProgressPojo> findByCourseId(ObjectId courseId);
}
