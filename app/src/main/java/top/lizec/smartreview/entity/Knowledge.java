package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class Knowledge {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer appType;
    private String title;
    private String content;
    private Integer difficulty;
    private Integer creator;
}