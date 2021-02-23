package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.KnowledgeReviewState;

public interface KnowledgeReviewStateDao {

    Integer insert(KnowledgeReviewState state);

    KnowledgeReviewState selectById(Integer kid);

    void updateReviewState(Integer kid, Integer memoryLevel);
}
