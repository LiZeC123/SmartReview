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
        Knowledge knowledge = dto.toKnowledge();
        knowledge.setCreator(userId);
        knowledgeDao.insert(knowledge);

        knowledgeTagService.create(dto.getTag(), userId, knowledge.getId());

        return knowledge;
    }

    public List<Knowledge> selectAll(Integer userId) {
        return knowledgeDao.selectAll(userId);
    }
}
