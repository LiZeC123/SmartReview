package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.Link;
import top.lizec.smartreview.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
public class KnowledgeVO {
    private Integer id;
    private Integer appType;
    private String title;
    private String content;
    private Integer difficulty;
    private Integer creator;
    private List<Tag> tags;
    private List<Link> links;


    public KnowledgeVO(Knowledge k) {
        this.id = k.getId();
        this.appType = k.getAppType();
        this.title = k.getTitle();
        this.content = k.getContent();
        this.difficulty = k.getDifficulty();
        this.creator = k.getCreator();
    }
}
