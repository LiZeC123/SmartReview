package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.entity.LevelDetail;

import java.util.List;

public interface ReviewStateDao {

    Integer insert(KnowledgeReviewState state);

    KnowledgeReviewState selectById(Integer kid);

    void updateReviewState(KnowledgeReviewState state);

}
