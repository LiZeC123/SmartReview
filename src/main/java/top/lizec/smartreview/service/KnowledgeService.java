package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.mapper.KnowledgeDao;

@Service
public class KnowledgeService {

    @Resource
    KnowledgeDao knowledgeDao;

    public Knowledge createKnowledge(Integer id, KnowledgeDto dto) {
        Knowledge knowledge = new Knowledge();
        knowledge.setTitle(dto.getTitle());
        knowledge.setContent(dto.getContent());
        knowledge.setCreator(id);
        knowledge.setLink(dto.getLink());

        knowledgeDao.insert(knowledge);

        return knowledge;
    }

    public List<Knowledge> selectAll(Integer userId) {
        return knowledgeDao.selectAll(userId);
    }
}
