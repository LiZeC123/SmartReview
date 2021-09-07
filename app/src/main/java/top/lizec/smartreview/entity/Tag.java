package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    Integer creator;


    public Tag(String name, Integer creator) {
        this.name = name;
        this.creator = creator;
    }

    public Tag(Integer tagId) {
        this.id = tagId;
    }
}
