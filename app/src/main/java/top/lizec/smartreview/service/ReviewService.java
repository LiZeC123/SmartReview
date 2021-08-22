package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeReviewDetail;
import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.mapper.ReviewDetailDao;
import top.lizec.smartreview.mapper.ReviewStateDao;

@Service
public class ReviewService {
    private static final Duration halfDay = Duration.ofHours(12);

    @Resource
    ReviewStateDao stateDao;

    @Resource
    ReviewDetailDao detailDao;

    @Resource
    UpdateReviewTimeServer updateReviewTimeServer;


    public void createReviewRecord(Integer kid) {
        KnowledgeReviewState state = new KnowledgeReviewState();
        state.setKnowledgeId(kid);
        state.setMemoryLevel(2);
        state.setIntervalTime(12);
        // 本地时间不涉及时区问题, 因此使用LocalDateTime
        state.setNextReviewTime(LocalDateTime.now().plus(halfDay));
        stateDao.insert(state);
    }


    public void updateReviewRecord(Knowledge k, Integer memoryLevel) {
        KnowledgeReviewState state = stateDao.selectById(k.getId());

        // 首先根据当前状态插入detail表
        KnowledgeReviewDetail detail = new KnowledgeReviewDetail(state);
        detail.setCurrentLevel(memoryLevel);
        detail.setDifficulty(k.getDifficulty());
        detailDao.insert(detail);

        // 然后更新状态
        state.increaseCount();
        state.setMemoryLevel(memoryLevel);
        int intervalTime = updateReviewTimeServer.nextReviewTime(detail);
        state.setIntervalTime(intervalTime);
        state.setNextReviewTime(LocalDateTime.now().plus(Duration.ofHours(intervalTime)));
        stateDao.updateReviewState(state);
    }
}
