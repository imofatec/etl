package com.imo.etl_imo.batch.reader;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.pojo.ProgressWithDetails;
import org.springframework.batch.item.ItemReader;

public class ProgressDetailsReader implements ItemReader<ProgressDetails> {

    private final ItemReader<ProgressWithDetails> progressReader;

    public ProgressDetailsReader(ItemReader<ProgressWithDetails> progressReader) {
        this.progressReader = progressReader;
    }

    @Override
    public ProgressDetails read() throws Exception {
        ProgressWithDetails progressWithDetails = progressReader.read();
        
        if (progressWithDetails == null) {
            return null;
        }

        return ProgressDetails.fromAggregation(
            progressWithDetails,
            progressWithDetails.getUserData(),
            progressWithDetails.getCourseData()
        );
    }
}
