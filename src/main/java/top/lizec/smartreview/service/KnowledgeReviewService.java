package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.KnowledgeReviewDetail;
import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.mapper.ReviewDetailDao;
import top.lizec.smartreview.mapper.ReviewStateDao;

@Service
public class KnowledgeReviewService {
    private static final Duration halfDay = Duration.ofHours(12);

    @Resource
    ReviewStateDao stateDao;

    @Resource
    ReviewDetailDao detailDao;


    void createReviewRecord(Integer kid) {
        KnowledgeReviewState state = new KnowledgeReviewState();
        state.setKnowledgeId(kid);
        state.setLastLevel(2);
        state.setCurrentLevel(2);
        state.setCurrentInterval(12);

        // 本地时间不涉及时区问题, 因此使用LocalDateTime
        state.setNextReviewTime(LocalDateTime.now().plus(halfDay));
        state.setFinished(false);

        stateDao.insert(state);
    }


    void updateReviewRecord(Integer kid, Integer memoryLevel) {
        KnowledgeReviewState state = stateDao.selectById(kid);
        KnowledgeReviewDetail detail = new KnowledgeReviewDetail();

        detail.setKnowledgeId(kid);
        detail.setLastLevel(state.getCurrentLevel());
        detail.setCurrentLevel(memoryLevel);

        detailDao.insert(detail);
        stateDao.updateReviewState(kid, memoryLevel);
    }

}
