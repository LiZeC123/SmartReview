package top.lizec.smartreview.service;

import top.lizec.smartreview.entity.KnowledgeReviewDetail;

public interface UpdateReviewTimeServer {

    int nextReviewTime(KnowledgeReviewDetail detail);
}
