package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeRecord;
import top.lizec.smartreview.mapper.KnowledgeDao;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KnowledgeService {

    @Resource
    KnowledgeTagService knowledgeTagService;

    @Resource
    KnowledgeReviewService knowledgeReviewService;

    @Resource
    KnowledgeDao knowledgeDao;

    @Transactional
    public Knowledge createKnowledge(Integer userId, KnowledgeDto dto) {
        Knowledge k = dto.toKnowledge();
        k.setCreator(userId);
        knowledgeDao.insert(k);
        knowledgeTagService.create(k.getTag(), userId, k.getId());
        knowledgeReviewService.createReviewRecord(k.getId());
        return k;
    }

    public List<Knowledge> selectAll(Integer userId) {
        return knowledgeDao.selectAll(userId);
    }


    public List<Knowledge> queryRecentReview(Integer userId) {
        return knowledgeDao.queryRecentReview(userId);
    }

    public void updateKnowledgeReview(Integer kid, Integer memoryLevel) {
        knowledgeReviewService.updateReviewRecord(kid, memoryLevel);
    }

    public List<KnowledgeRecord> queryAllRecord(Integer userId) {
        return knowledgeDao.queryAllRecord(userId);
    }
}
