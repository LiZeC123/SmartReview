package top.lizec.smartreview.service;

import top.lizec.smartreview.entity.ReviewDetail;

public interface UpdateReviewTimeServer {

    int nextReviewTime(ReviewDetail detail);
}
