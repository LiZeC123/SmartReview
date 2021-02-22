package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.mapper.KnowledgeDao;

@Service
public class KnowledgeService {

    @Resource
    KnowledgeTagService knowledgeTagService;

    @Resource
    KnowledgeDao knowledgeDao;

    @Transactional
    public Knowledge createKnowledge(Integer userId, KnowledgeDto dto) {
        Knowledge k = dto.toKnowledge();
        k.setCreator(userId);
        knowledgeDao.insert(k);
        knowledgeTagService.create(k.getTag(), userId, k.getId());
        return k;
    }

    public List<Knowledge> selectAll(Integer userId) {
        return knowledgeDao.selectAll(userId);
    }
}
