package com.imo.etl_imo.processor;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.dto.AnalyticsDetails;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CompletionProbabilityProcessor implements ItemProcessor<ProgressDetails, AnalyticsDetails> {

    private final CompletionScoreCalculator scoreCalculator;

    public CompletionProbabilityProcessor(CompletionScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public AnalyticsDetails process(ProgressDetails item) {
        double completionScore = scoreCalculator.calculateScore(item);
        double completionProbability = completionScore / 100.0;
        item.setCompletionProbability(completionProbability);

        return AnalyticsDetails.from(item);
    }
}
