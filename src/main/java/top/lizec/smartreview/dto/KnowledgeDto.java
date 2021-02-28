package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import top.lizec.smartreview.entity.Knowledge;

import java.util.Arrays;
import java.util.stream.Collectors;

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

        if (StringUtils.hasText(k.getLink())) {
            this.link = Arrays.stream(k.getLink().split("\n"))
                    .map(LinkPair::new).toArray(LinkPair[]::new);
        } else {
            this.link = new LinkPair[0];
        }

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
