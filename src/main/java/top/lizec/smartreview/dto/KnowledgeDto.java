package top.lizec.smartreview.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.lizec.smartreview.entity.Knowledge;

@Data
@NoArgsConstructor
public class KnowledgeDto {
    private Integer id;
    private String appType;
    private String title;
    private String content;
    private LinkPair[] link;
    private String[] tag;


    public KnowledgeDto(Knowledge k) {
        this.id = k.getId();
        this.appType = k.getAppType();
        this.title = k.getTitle();
        this.content = k.getContent();
        this.link = Arrays.stream(k.getLink().split("\n"))
                .map(LinkPair::new).toArray(LinkPair[]::new);
        this.tag = k.getTag().split(";");
    }

    public Knowledge toKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setAppType(this.getAppType());
        knowledge.setTitle(this.getTitle());
        knowledge.setContent(this.getContent());

        String link = Arrays.stream(this.link)
                .map(pair -> pair.getName() + " --> " + pair.getUrl())
                .collect(Collectors.joining("\n"));
        knowledge.setLink(link);

        String tag = String.join(";", this.tag);
        knowledge.setTag(tag);

        return knowledge;
    }
}
