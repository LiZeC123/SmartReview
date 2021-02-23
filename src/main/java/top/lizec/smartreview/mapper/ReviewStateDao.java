package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.entity.Rate;

public interface ReviewStateDao {

    Integer insert(KnowledgeReviewState state);

    KnowledgeReviewState selectById(Integer kid);

    void updateReviewState(Integer kid, Integer memoryLevel);

    Integer getKnowledgeCount();

    List<KnowledgeReviewState> selectNeedUpdateBetween(int begin, int end);

    Integer updateReviewTimeInfo(List<KnowledgeReviewState> states);

    List<Rate> loadSimpleReviewRate();
}
