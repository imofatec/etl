package com.imo.etl_imo.repository;

import com.imo.etl_imo.model.pojo.UserPojo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserPojo, ObjectId> {

    Optional<UserPojo> findByEmail(String email);
}
