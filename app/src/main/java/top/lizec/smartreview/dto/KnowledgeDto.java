package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import top.lizec.smartreview.dto.LinkDto;
import top.lizec.smartreview.dto.TagDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeTag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class KnowledgeDto {
    private Integer id;
    private Integer appType;
    private String title;
    private String content;
    private List<LinkDto> link;
    private List<Integer> tag;

    // 扩展字段
    private List<String> words;



//    public KnowledgeDto(Knowledge k) {
//        this.id = k.getId();
//        this.appType = k.getAppType();
//        this.title = k.getTitle();
//        this.content = k.getContent();
//        //this.link = LinkDto.fromListJson(k.getLink());
//    }
//
//    public Knowledge toKnowledge() {
//        Knowledge knowledge = new Knowledge();
//        knowledge.setAppType(appType);
//        knowledge.setTitle(title);
//        knowledge.setContent(content);
//        //knowledge.setLink(LinkDto.toJson(link));
//        knowledge.setDifficulty(difficulty);
//
//        return knowledge;
//    }


}
