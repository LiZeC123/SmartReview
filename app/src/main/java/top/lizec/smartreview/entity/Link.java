package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer knowledgeId;
    private String name;
    private String url;

    public Link(Integer knowledgeId, String name, String url) {
        this.knowledgeId = knowledgeId;
        this.name = name;
        this.url = url;
    }
}