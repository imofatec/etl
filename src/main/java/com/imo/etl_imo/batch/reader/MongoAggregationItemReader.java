package com.imo.etl_imo.batch.reader;

import com.imo.etl_imo.model.pojo.ProgressWithDetails;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;

public class MongoAggregationItemReader implements ItemReader<ProgressWithDetails> {

    private final MongoTemplate mongoTemplate;
    private final int pageSize;
    private List<ProgressWithDetails> currentPage;
    private int currentIndex;
    private int skip;
    private boolean hasMoreData;

    public MongoAggregationItemReader(MongoTemplate mongoTemplate, int pageSize) {
        this.mongoTemplate = mongoTemplate;
        this.pageSize = pageSize;
        this.currentPage = new ArrayList<>();
        this.currentIndex = 0;
        this.skip = 0;
        this.hasMoreData = true;
    }

    @Override
    public ProgressWithDetails read() throws Exception {
        if (currentIndex >= currentPage.size() && hasMoreData) {
            fetchNextPage();
        }

        if (currentIndex < currentPage.size()) {
            return currentPage.get(currentIndex++);
        }

        return null;
    }

    private void fetchNextPage() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup("users", "userId", "_id", "user"),
            Aggregation.lookup("courses", "courseId", "_id", "course"),
            Aggregation.sort(Sort.Direction.DESC, "createdAt"),
            Aggregation.skip((long) skip),
            Aggregation.limit(pageSize)
        );

        AggregationResults<ProgressWithDetails> results = 
            mongoTemplate.aggregate(aggregation, "progress", ProgressWithDetails.class);

        currentPage = results.getMappedResults();
        currentIndex = 0;
        skip += pageSize;

        hasMoreData = currentPage.size() == pageSize;
    }
}
