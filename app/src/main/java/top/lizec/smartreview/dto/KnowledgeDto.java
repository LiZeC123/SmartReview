package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

}
