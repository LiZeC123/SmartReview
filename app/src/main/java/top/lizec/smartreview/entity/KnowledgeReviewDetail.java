package top.lizec.smartreview.entity;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class KnowledgeReviewDetail {
    Integer id;
    Integer knowledgeId;
    Integer reviewCount;
    Integer lastLevel;
    Integer currentLevel;
    Integer difficulty;
    Integer intervalTime;
    Integer elapsedTime;


    public KnowledgeReviewDetail(KnowledgeReviewState state) {
        this.knowledgeId = state.getKnowledgeId();
        this.reviewCount = state.getReviewCount() + 1;
        this.lastLevel = state.getMemoryLevel();
        this.intervalTime = state.getIntervalTime();
        this.elapsedTime = (int) Duration.between(state.getNextReviewTime(), LocalDateTime.now()).toHours();
    }
}
