package top.lizec.smartreview.entity;


import java.time.LocalDateTime;

import lombok.Data;


@Data
public class KnowledgeReviewDetail {
    Integer id;
    Integer knowledgeId;
    Integer lastLevel;
    Integer currentLevel;
    LocalDateTime createTime;
}
