package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.ReviewDetail;
import top.lizec.smartreview.entity.ReviewRecord;
import top.lizec.smartreview.entity.ReviewState;
import top.lizec.smartreview.mapper.ReviewDetailDao;
import top.lizec.smartreview.mapper.ReviewStateDao;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    //    private static final Duration halfDay = Duration.ofHours(12);
    //TODO: DEBUG临时调整为1秒
    private static final Duration halfDay = Duration.ofSeconds(1);

    @Resource
    ReviewStateDao stateDao;

    @Resource
    ReviewDetailDao detailDao;

    @Resource
    UpdateReviewTimeServer updateReviewTimeServer;

    @Resource
    private CheckService checkService;

    @Transactional
    public void createReviewRecord(Knowledge k) {
        ReviewState state = new ReviewState();
        state.setKnowledgeId(k.getId());
        state.setReviewCount(0);
        state.setMemoryLevel(2);
        state.setIntervalTime(12);

        final ZonedDateTime zonedDateTime = LocalDateTime.now().plus(halfDay).atZone(ZoneId.systemDefault());
        state.setNextReviewTime(Date.from(zonedDateTime.toInstant()));

        stateDao.insert(state);
    }

    @Transactional
    public void updateKnowledgeReview(Integer userId, Integer kid, Integer memoryLevel) {
        checkService.checkKnowledgePermission(userId, kid);
        updateReviewRecord(kid, memoryLevel);
    }

    private void updateReviewRecord(Integer kid, Integer memoryLevel) {
        ReviewState state = stateDao.selectById(kid);

        // 首先根据当前状态插入detail表
        ReviewDetail detail = new ReviewDetail(state);
        detail.setCurrentLevel(memoryLevel);
        detailDao.insert(detail);

        // 然后更新状态
        state.increaseCount();
        state.setMemoryLevel(memoryLevel);
        int intervalTime = updateReviewTimeServer.nextReviewTime(detail);
        state.setIntervalTime(intervalTime);

        final ZonedDateTime zonedDateTime = LocalDateTime.now().plus(Duration.ofHours(intervalTime)).atZone(ZoneId.systemDefault());
        state.setNextReviewTime(Date.from(zonedDateTime.toInstant()));
        stateDao.updateReviewState(state);
    }

    public List<ReviewRecord> queryAllRecord(Integer userId) {
        return stateDao.selectAllRecord(userId);
    }
}
