package top.lizec.smartreview.schedule;

import top.lizec.smartreview.entity.KnowledgeReviewState;

interface StateUpdater {

    /**
     * 更新状态
     *
     * @param state 更新前的状态
     * @return 根据策略更新后的状态
     */
    KnowledgeReviewState update(KnowledgeReviewState state);
}
