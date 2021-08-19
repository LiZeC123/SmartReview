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


    public void createReviewRecord(Integer kid) {
        KnowledgeReviewState state = new KnowledgeReviewState();
        state.setKnowledgeId(kid);
        state.setCurrentLevel(2);
        state.setCurrentInterval(12);
        // 本地时间不涉及时区问题, 因此使用LocalDateTime
        state.setNextReviewTime(LocalDateTime.now().plus(halfDay));

        stateDao.insert(state);
    }


    public void updateReviewRecord(Integer kid, Integer memoryLevel) {
        KnowledgeReviewState state = stateDao.selectById(kid);
        KnowledgeReviewDetail detail = new KnowledgeReviewDetail();

        detail.setKnowledgeId(kid);
        detail.setLastLevel(state.getCurrentLevel());
        detail.setCurrentLevel(memoryLevel);

        detailDao.insert(detail);
        //TODO: 迁移完成此部分算法
        //stateDao.updateReviewState(updateState(state, memoryLevel));
    }

//    private KnowledgeReviewState updateState(KnowledgeReviewState oldState, Integer newLevel) {
//        double[][] rate = rateCache.getValue();
//
//        KnowledgeReviewState newState = new KnowledgeReviewState();
//        newState.setKnowledgeId(oldState.getKnowledgeId());
//        newState.setReviewCount(oldState.getReviewCount() + 1);
//
//        int interval = (int) (oldState.getCurrentInterval() * rate[oldState.getCurrentLevel()][newLevel]);
//        interval = Math.max(1, interval);    //时间间隔至少为1, 以免产生0导致后续无法更新
//        newState.setCurrentInterval(interval);
//        newState.setCurrentLevel(newLevel);
//        newState.setNextReviewTime(LocalDateTime.now().plus(Duration.ofHours(interval)));
//
//        return newState;
//    }
}
