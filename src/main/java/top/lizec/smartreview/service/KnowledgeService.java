package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeRecord;
import top.lizec.smartreview.exception.NoPermissionException;
import top.lizec.smartreview.mapper.KnowledgeDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
        k.setTag(k.getTag() + ";" + appTypeToTag(k.getAppType()));
        knowledgeDao.insert(k);
        knowledgeTagService.create(k.getTag(), userId, k.getId());
        knowledgeReviewService.createReviewRecord(k.getId());
        return k;
    }

    private String appTypeToTag(String appType) {
        switch (appType) {
            case "Base":
                return "默认分类";
            case "EnglishWordBook":
                return "英语单词本";
            case "LeetCodeNote":
                return "力扣题解";
            default:
                throw new IllegalArgumentException("未定义的APP类型");
        }
    }

    public List<Knowledge> selectAll(Integer userId) {
        return knowledgeDao.selectAll(userId);
    }


    public List<Knowledge> queryRecentReview(Integer userId) {
        return knowledgeDao.queryRecentReview(userId);
    }

    public void updateKnowledgeReview(Integer userId, Integer kid, Integer memoryLevel) {
        checkUserPermission(userId, kid);
        knowledgeReviewService.updateReviewRecord(kid, memoryLevel);
    }

    public List<KnowledgeRecord> queryAllRecord(Integer userId) {
        return knowledgeDao.queryAllRecord(userId);
    }

    public Optional<KnowledgeDto> selectOne(Integer userId, Integer kid) {
        checkUserPermission(userId, kid);
        Knowledge k = knowledgeDao.selectOne(kid);
        return Optional.ofNullable(k).map(KnowledgeDto::new);
    }

    public void deleteKnowledge(Integer userId, Integer kid) {
        checkUserPermission(userId, kid);
        knowledgeDao.delete(kid);
    }


    private void checkUserPermission(Integer userId, Integer kid) {
        if (!knowledgeDao.checkUserPermission(userId, kid)) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }
}
