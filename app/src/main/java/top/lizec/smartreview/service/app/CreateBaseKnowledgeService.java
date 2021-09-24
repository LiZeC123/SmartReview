package top.lizec.smartreview.service.app;

import org.springframework.stereotype.Service;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;

import java.util.Collections;
import java.util.List;

@Service
public class CreateBaseKnowledgeService implements CreateKnowledgeService {
    private static final int APP_TYPE = 1;

    @Override
    public List<Knowledge> parseKnowledgeDto(KnowledgeDto dto) {
        Knowledge k = new Knowledge();
        k.setAppType(APP_TYPE);
        k.setTitle(dto.getTitle());
        k.setContent(dto.getContent());
        k.setDifficulty(2);

        return Collections.singletonList(k);
    }

    @Override
    public void afterInsertKnowledge(Knowledge knowledges, KnowledgeDto dto) {

    }
}
