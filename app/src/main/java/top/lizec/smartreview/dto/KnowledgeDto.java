package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeTag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class KnowledgeDto {
    private Integer id;
    private String appType;
    private String title;
    private String content;
    private String link;
    private Integer difficulty;
    private List<TagDto> tag;


    public KnowledgeDto(Knowledge k) {
        this.id = k.getId();
        this.appType = k.getAppType();
        this.title = k.getTitle();
        this.content = k.getContent();
        this.link = k.getLink();
    }

    public Knowledge toKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setAppType(appType);
        knowledge.setTitle(title);
        knowledge.setContent(content);
        knowledge.setLink(link);
        knowledge.setDifficulty(difficulty);

        return knowledge;
    }


}
