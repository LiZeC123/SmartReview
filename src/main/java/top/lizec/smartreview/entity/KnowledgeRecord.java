package top.lizec.smartreview.entity;

import lombok.Data;

@Data
public class KnowledgeRecord {
    Integer id;
    String appType;
    String title;
    Integer reviewCount;
    Integer currentInterval;
    String nextReviewTime;
}
