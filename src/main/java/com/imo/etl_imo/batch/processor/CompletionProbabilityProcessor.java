package com.imo.etl_imo.batch.processor;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.util.NumberFormatter;
import com.imo.etl_imo.model.dto.AnalyticsDetails;
import com.imo.etl_imo.batch.processor.calculator.CompletionScoreCalculator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class CompletionProbabilityProcessor implements ItemProcessor<ProgressDetails, AnalyticsDetails> {

    private final CompletionScoreCalculator scoreCalculator;

    public CompletionProbabilityProcessor(CompletionScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public AnalyticsDetails process(ProgressDetails item) {
        
        double completionScore = scoreCalculator.calculateScore(item);
        BigDecimal completionProbability = NumberFormatter.formatNumberToBigDecimal((completionScore / 100), 3);
        item.setCompletionProbability(completionProbability);

        return AnalyticsDetails.from(item);
    }
}
