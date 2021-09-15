package top.lizec.smartreview.service.app;

import org.springframework.transaction.annotation.Transactional;

import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;

import java.util.List;

public interface CreateKnowledgeService {

    /**
     * 将前端提交的知识点对象解析为数据库存储的知识点对象
     * @param dto 原始的知识点
     * @return 解析后的知识点列表
     */
    List<Knowledge> parseKnowledgeDto(KnowledgeDto dto);

    /**
     * 知识点插入数据库后执行的回调方法
     * @param knowledges 已经插入数据库中的知识点
     * @param dto 知识点对应的原始对象
     */
    @Transactional
    void afterInsertKnowledge(Knowledge knowledges, KnowledgeDto dto);
}
