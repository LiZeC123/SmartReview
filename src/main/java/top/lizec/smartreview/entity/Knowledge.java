package top.lizec.smartreview.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value="top.lizec.smartreview.entity.Knowledge知识信息表")
@Data
public class Knowledge implements Serializable {
    private Integer id;

    private String title;

    private String content;

    private Date createTime;

    private Date modifiedTime;

    private Byte isDelete;

    private static final long serialVersionUID = 1L;
}