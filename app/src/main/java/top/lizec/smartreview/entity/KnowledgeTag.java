package top.lizec.smartreview.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.lizec.smartreview.dto.TagDto;

@Data
@NoArgsConstructor
public class KnowledgeTag {
    Integer knowledgeId;
    Integer tagId;
    String name;
    Integer creator;

    public KnowledgeTag(TagDto tag, Integer uid, Integer kid) {
        this.knowledgeId = kid;
        this.tagId = tag.getId();
        this.name = tag.getName();
        this.creator = uid;
    }

    public TagDto toTagDto() {
        TagDto dto = new TagDto();
        dto.setId(tagId);
        dto.setName(name);
        return dto;
    }

}
