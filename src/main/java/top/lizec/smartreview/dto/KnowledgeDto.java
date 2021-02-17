package top.lizec.smartreview.dto;

import lombok.Data;
import top.lizec.smartreview.entity.Knowledge;

@Data
public class KnowledgeDto {
    private String appType;
    private String title;
    private String content;
    private String link;
    private String tag;

    public Knowledge toKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setAppType(this.getAppType());
        knowledge.setTitle(this.getTitle());
        knowledge.setContent(this.getContent());
        knowledge.setLink(this.getLink());
        knowledge.setTag(this.getTag());

        return knowledge;
    }
}
