package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sentence {
    @TableId(type = IdType.AUTO)
    Integer id;
    String content;

    public Sentence(String content) {
        this.content = content;
    }
}
