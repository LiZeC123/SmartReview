package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.dto.TagDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeRecord;
import top.lizec.smartreview.entity.KnowledgeTag;
import top.lizec.smartreview.exception.NoPermissionException;
import top.lizec.smartreview.mapper.KnowledgeDao;

@Service
public class KnowledgeService {

    @Resource
    private TagService tagService;

    @Resource
    private KnowledgeReviewService knowledgeReviewService;

    @Resource
    private KnowledgeDao knowledgeDao;

//    @Resource
//    private Cache<Integer, KnowledgeDto> knowledgeCache;


    @Transactional
    public void createKnowledge(Integer userId, KnowledgeDto dto) {
        Knowledge k = dto.toKnowledge();
        k.setCreator(userId);
        knowledgeDao.insert(k);
        tagService.createKnowledgeTag(dto.getTag(), userId, k.getId());
        knowledgeReviewService.createReviewRecord(k.getId());
    }

    public List<KnowledgeDto> queryRecentReview(Integer userId) {
        List<Knowledge> knowledges = knowledgeDao.queryRecentReview(userId, LocalDateTime.now());

        return loadTag(knowledges);
    }


    public List<KnowledgeDto> selectAll(Integer userId) {
        List<Knowledge> knowledges = knowledgeDao.selectAll(userId);
        return loadTag(knowledges);
    }


    private List<KnowledgeDto> loadTag(List<Knowledge> knowledges) {
        List<Integer> kids = knowledges.stream().map(Knowledge::getId).collect(Collectors.toList());

        Map<Integer, List<KnowledgeTag>> tagMap = tagService.selectTagByKnowledge(kids);

        List<KnowledgeDto> ans = new ArrayList<>(knowledges.size());
        for (Knowledge knowledge : knowledges) {
            KnowledgeDto dto = new KnowledgeDto(knowledge);
            List<TagDto> tags = tagMap.getOrDefault(knowledge.getId(), Collections.emptyList()).stream()
                    .map(KnowledgeTag::toTagDto).collect(Collectors.toList());
            dto.setTag(tags);
            ans.add(dto);
        }

        return ans;
    }


    //TODO: 从下面开始修改
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
