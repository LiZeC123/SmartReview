package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.KnowledgeReviewDetail;

public interface ReviewDetailDao {

    Integer insert(KnowledgeReviewDetail detail);

    List<KnowledgeReviewDetail> selectUserYesterdayDetails(Integer userId);
}
