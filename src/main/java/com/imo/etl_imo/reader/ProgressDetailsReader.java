package com.imo.etl_imo.reader;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.pojo.CoursePojo;
import com.imo.etl_imo.model.pojo.ProgressPojo;
import com.imo.etl_imo.model.pojo.UserPojo;
import com.imo.etl_imo.repository.CourseRepository;
import com.imo.etl_imo.repository.UserRepository;
import org.springframework.batch.item.ItemReader;

public class ProgressDetailsReader implements ItemReader<ProgressDetails> {

    private final ItemReader<ProgressPojo> progressReader;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ProgressDetailsReader(
            ItemReader<ProgressPojo> progressReader,
            UserRepository userRepository,
            CourseRepository courseRepository) {

        this.progressReader = progressReader;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ProgressDetails read() throws Exception {
        ProgressPojo progress = progressReader.read();
        
        if (progress == null) {
            return null;
        }

        UserPojo user = userRepository.findById(progress.getUserId()).orElse(null);

        CoursePojo course = courseRepository.findById(progress.getCourseId()).orElse(null);
        
        return ProgressDetails.from(progress, user, course);
    }
}
