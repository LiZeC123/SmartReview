package top.lizec.smartreview.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class AppType implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String comp;

    private static final long serialVersionUID = 1L;
}