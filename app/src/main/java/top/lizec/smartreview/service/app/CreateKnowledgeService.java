package top.lizec.smartreview.service.app;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;

import java.util.List;

public interface CreateKnowledgeService {

    List<Knowledge> parseKnowledgeDto(KnowledgeDto dto);


    void afterInsertKnowledge(Knowledge knowledges, KnowledgeDto dto);
}
