package top.lizec.smartreview.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KnowledgeReviewState {
    Integer knowledgeId;
    Integer reviewCount;
    Integer lastLevel;
    Integer currentLevel;
    Integer currentInterval;
    LocalDateTime nextReviewTime;
    Boolean finished;
    LocalDateTime finishedTime;
}
