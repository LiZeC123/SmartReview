package top.lizec.smartreview.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KnowledgeReviewState {
    Integer knowledgeId;
    Integer reviewCount;
    Integer currentLevel;
    Integer currentInterval;
    LocalDateTime nextReviewTime;
}
